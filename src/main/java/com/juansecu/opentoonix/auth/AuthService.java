package com.juansecu.opentoonix.auth;

import java.text.SimpleDateFormat;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.server.ResponseStatusException;

import com.juansecu.opentoonix.auth.dtos.requests.LoginReqDto;
import com.juansecu.opentoonix.auth.dtos.requests.RegisterReqDto;
import com.juansecu.opentoonix.auth.dtos.responses.IsValidUsernameResDto;
import com.juansecu.opentoonix.auth.dtos.responses.LoginResDto;
import com.juansecu.opentoonix.auth.enums.ELoggingInError;
import com.juansecu.opentoonix.avatars.AvatarsService;
import com.juansecu.opentoonix.avatars.dtos.requests.NewAvatarReqDto;
import com.juansecu.opentoonix.shared.adapters.JwtAdapter;
import com.juansecu.opentoonix.shared.models.CookieModel;
import com.juansecu.opentoonix.shared.providers.HostDetailsProvider;
import com.juansecu.opentoonix.usernameblacklist.UsernameBlacklistService;
import com.juansecu.opentoonix.users.repositories.IUsersRepository;
import com.juansecu.opentoonix.users.repositories.IUsersInformationRepository;
import com.juansecu.opentoonix.users.models.UserInformationModel;
import com.juansecu.opentoonix.users.models.entities.UserEntity;
import com.juansecu.opentoonix.users.models.views.UserInformationView;

@RequiredArgsConstructor
@Service
public class AuthService {
    public static final String USER_AUTHENTICATION_COOKIE_NAME = "AUTH_TOKEN";

    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(AuthService.class);

    private final AvatarsService avatarsService;
    private final HostDetailsProvider hostDetailsProvider;
    private final JwtAdapter jwtAdapter;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final IUsersInformationRepository userInformationRepository;
    private final UsernameBlacklistService usernameBlacklistService;
    private final IUsersRepository usersRepository;

    @Transactional
    public void createUser(final RegisterReqDto registerReqDto) {
        NewAvatarReqDto newAvatarReqDto;
        UserEntity newUser;

        final String existentEmail = this.usersRepository.findEmail(
            registerReqDto.getEmail()
        );
        final String existentUsername = this.usersRepository.findUsername(
            registerReqDto.getUsername()
        );

        AuthService.CONSOLE_LOGGER.info(
            "Validating if email and username are already in use..."
        );

        if (existentEmail != null) {
            AuthService.CONSOLE_LOGGER.error("Email is already in use");

            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Email is already in use"
            );
        }

        if (existentUsername != null) {
            AuthService.CONSOLE_LOGGER.error("Username is already in use");

            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Username is already in use"
            );
        }

        newUser = new UserEntity();

        newUser.setUsername(registerReqDto.getUsername());
        newUser.setEmail(registerReqDto.getEmail().toLowerCase());
        newUser.setAge(Integer.parseInt(registerReqDto.getAge()));
        newUser.setGender(registerReqDto.getGender());
        newUser.setPassword(this.passwordEncoder.encode(registerReqDto.getPassword()));

        try {
            newAvatarReqDto = new NewAvatarReqDto().getNewAvatarReqDtoFromString(
                registerReqDto.getAvatar()
            );

            newUser.setBirthdate(
                new SimpleDateFormat("yyyy-MM-dd").parse(registerReqDto.getBirthdate())
            );

            newUser = this.usersRepository.save(newUser);

            this.avatarsService.createAvatar(newAvatarReqDto, newUser);

            AuthService.CONSOLE_LOGGER.info("User created successfully");
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            AuthService.CONSOLE_LOGGER.error(
                "There was an unexpected error while trying to register new user: {}",
                exception.getMessage()
            );

            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "There was an unexpected error while trying to register new user"
            );
        }
    }

    public IsValidUsernameResDto isValidUsername(final String username) {
        final String existentUsername = this.usersRepository.findUsername(username);
        final IsValidUsernameResDto isValidUsernameResDto = new IsValidUsernameResDto();
        final boolean isUsernameInBlackList = this.usernameBlacklistService.isUsernameInBlacklist(
            username
        );

        if (existentUsername == null && !isUsernameInBlackList) {
            isValidUsernameResDto.setExistsUsername(false);
            isValidUsernameResDto.setUsernameInBlackList(false);
        } else if (existentUsername != null)
            isValidUsernameResDto.setExistsUsername(true);
        else if (isUsernameInBlackList)
            isValidUsernameResDto.setUsernameInBlackList(true);

        return isValidUsernameResDto;
    }

    public LoginResDto login(
        final LoginReqDto loginReqDto,
        final HttpServletRequest request
    ) {
        boolean passwordMatches;
        UserInformationView userInformation;

        final UserEntity user = (UserEntity) this.userDetailsService.loadUserByUsername(
            loginReqDto.getUsername()
        );

        AuthService.CONSOLE_LOGGER.info(
            "Checking if user {} exists...",
            loginReqDto.getUsername()
        );

        if (user == null) {
            AuthService.CONSOLE_LOGGER.error(
                "User {} does not exist",
                loginReqDto.getUsername()
            );

            return new LoginResDto(
                ELoggingInError.USER_NOT_EXISTS,
                false,
                null
            );
        }

        if (!user.isEnabled()) {
            AuthService.CONSOLE_LOGGER.error(
                "User {} is disabled",
                loginReqDto.getUsername()
            );

            return new LoginResDto(
                ELoggingInError.USER_DISABLED,
                false,
                null
            );
        }

        passwordMatches = passwordEncoder.matches(
            loginReqDto.getPassword(),
            user.getPassword()
        );

        if (!passwordMatches) {
            AuthService.CONSOLE_LOGGER.error("User password is incorrect");

            return new LoginResDto(
                ELoggingInError.USER_WRONG_PASSWORD,
                false,
                null
            );
        }

        userInformation = this.userInformationRepository.findByUsernameIgnoreCase(
            user.getUsername()
        );

        AuthService.CONSOLE_LOGGER.info(
            "User {} logged in successfully",
            loginReqDto.getUsername()
        );

        return new LoginResDto(
            null,
            true,
            new UserInformationModel(
                userInformation,
                new CookieModel(
                    AuthService.USER_AUTHENTICATION_COOKIE_NAME,
                    "/",
                    this.jwtAdapter.generateJsonWebToken(
                        userInformation.getUsername(),
                        request.getRequestURL().toString()
                    )
                ),
                this.hostDetailsProvider.getHostPath()
            )
        );
    }
}
