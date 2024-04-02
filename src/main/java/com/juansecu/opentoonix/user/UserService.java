package com.juansecu.opentoonix.user;

/* --- Java modules --- */
import java.text.SimpleDateFormat;

/* --- Javax modules --- */
import javax.servlet.http.HttpServletRequest;

/* --- Third-party modules --- */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/* --- Application modules --- */
import com.juansecu.opentoonix.avatar.AvatarService;
import com.juansecu.opentoonix.avatar.dtos.requests.NewAvatarReqDto;
import com.juansecu.opentoonix.shared.adapters.JwtAdapter;
import com.juansecu.opentoonix.shared.models.CookieModel;
import com.juansecu.opentoonix.shared.providers.HostDetailsProvider;
import com.juansecu.opentoonix.user.daos.IUserDao;
import com.juansecu.opentoonix.user.daos.IUserInformationDao;
import com.juansecu.opentoonix.user.dtos.requests.LoginReqDto;
import com.juansecu.opentoonix.user.dtos.requests.NewUserReqDto;
import com.juansecu.opentoonix.user.dtos.responses.IsValidUsernameResDto;
import com.juansecu.opentoonix.user.dtos.responses.LoginResDto;
import com.juansecu.opentoonix.user.enums.ELoggingInError;
import com.juansecu.opentoonix.user.models.UserInformationModel;
import com.juansecu.opentoonix.user.models.entities.UserEntity;
import com.juansecu.opentoonix.user.models.views.UserInformationView;
import com.juansecu.opentoonix.usernameblacklist.UsernameBlacklistService;
import com.juansecu.opentoonix.mail.OnRegistrationCompleteEvent;
import com.juansecu.opentoonix.mail.VerificationToken;
import com.juansecu.opentoonix.mail.VerificationTokenRepository;

@Service
public class    UserService {
    public static final String USER_AUTHENTICATION_COOKIE_NAME = "AUTH_TOKEN";

    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    private AvatarService avatarService;
    @Autowired
    private HostDetailsProvider hostDetailsProvider;
    @Autowired
    private JwtAdapter jwtAdapter;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserInformationDao userInformationDao;
    @Autowired
    private UsernameBlacklistService usernameBlacklistService;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Transactional
    public void createUser(NewUserReqDto newUserReqDto) {
        UserService.CONSOLE_LOGGER.info("Initializing register service...");

        final String userEmail = this.userDao.findEmail(newUserReqDto.getEmail().toLowerCase());

        if (userEmail == null) {
            UserEntity newUser = new UserEntity();

            final NewAvatarReqDto newAvatarReqDto = new NewAvatarReqDto().getNewAvatarReqDtoFromString(
                newUserReqDto.getAvatar()
            );

            try {
                newUser.setUsername(newUserReqDto.getUsername().toLowerCase());
                newUser.setEmail(newUserReqDto.getEmail().toLowerCase());
                newUser.setAge(Integer.parseInt(newUserReqDto.getAge()));
                newUser.setBirthdate(
                    new SimpleDateFormat("yyyy-MM-dd").parse(newUserReqDto.getBirthdate())
                );
                newUser.setGender(newUserReqDto.getGender());
                newUser.setPassword(this.passwordEncoder.encode(newUserReqDto.getPassword()));
                newUser.setEnabled(false);  //will be enabled on E-Mail verification

                newUser = this.userDao.save(newUser);

                this.avatarService.createAvatar(newAvatarReqDto, newUser);

                UserService.CONSOLE_LOGGER.info("User created successfully");

                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(newUser));
            } catch (Exception exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                UserService.CONSOLE_LOGGER.error(
                    String.format(
                        "Error while trying to register new user:%n%s",
                        exception
                    )
                );;
            }
        }
    }

    /**
     * Verifies if the given username is currently being used
     * and if it is part of the username blacklist.
     *
     * @param username The username to verify
     * @return IsValidUsernameResDto
     */
    public IsValidUsernameResDto isValidUsername(final String username) {
        final String existentUsername = this.userDao.findUsername(username.toLowerCase());
        final IsValidUsernameResDto isValidUsernameResDto = new IsValidUsernameResDto();
        final boolean isUsernameInBlackList = this.usernameBlacklistService.isUsernameInBlacklist(username.toLowerCase());

        if (existentUsername == null && !isUsernameInBlackList) {
            isValidUsernameResDto.setExistsUsername(false);
            isValidUsernameResDto.setUsernameInBlackList(false);
        } else if (existentUsername != null) isValidUsernameResDto.setExistsUsername(true);
        else if (isUsernameInBlackList) isValidUsernameResDto.setUsernameInBlackList(true);

        return isValidUsernameResDto;
    }

    public LoginResDto login(final LoginReqDto loginReqDto, final HttpServletRequest request) {
        UserService.CONSOLE_LOGGER.info("Initializing login service...");

        boolean passwordMatches;
        UserInformationView userInformation;

        final UserEntity user = this.userDao.findByUsername(loginReqDto.getUsername());

        if (user == null) {
            UserService.CONSOLE_LOGGER.error("User does not exist");

            return new LoginResDto(
                ELoggingInError.USER_NOT_EXISTS,
                false,
                null
            );
        }

        passwordMatches = passwordEncoder.matches(
            loginReqDto.getPassword(),
            user.getPassword()
        );

        if (!passwordMatches) {
            UserService.CONSOLE_LOGGER.error("User password is incorrect");

            return new LoginResDto(
                ELoggingInError.USER_WRONG_PASSWORD,
                false,
                null
            );
        }

        if (!user.getEnabled()) {
            UserService.CONSOLE_LOGGER.error("User is not enabled");

            return new LoginResDto(
                ELoggingInError.USER_NOT_ENABLED,
                false,
                null
            );
        }

        userInformation = this.userInformationDao.findByUsername(user.getUsername());

        return userInformation != null
            ? new LoginResDto(
                null,
                true,
                new UserInformationModel(
                    userInformation,
                    new CookieModel(
                        UserService.USER_AUTHENTICATION_COOKIE_NAME,
                        "/",
                        this.jwtAdapter.generateJsonWebToken(
                            userInformation.getUsername(),
                            request.getRequestURL().toString()
                        )
                    ),
                    this.hostDetailsProvider.getHostPath()
                )
            )
            : new LoginResDto(
                ELoggingInError.USER_DISABLED,
                false,
                null
            );
    }

    public void saveRegisteredUser(UserEntity user) {
        userDao.save(user);
    }

    public void createVerificationToken(UserEntity user, String token) {
        VerificationToken myToken = new VerificationToken();
        myToken.setToken(token);
        myToken.setUser(user);
        myToken.setExpiryDate(myToken.calculateExpiryDate());
        tokenRepository.save(myToken);
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
}
