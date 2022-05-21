package com.juansecu.opentoonix.user;

/* --- Java modules --- */
import java.text.SimpleDateFormat;
import java.util.Date;

/* --- Javax modules --- */
import javax.servlet.http.HttpServletRequest;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/* --- Application modules --- */
import com.juansecu.opentoonix.avatar.AvatarService;
import com.juansecu.opentoonix.avatar.dtos.requests.NewAvatarReqDto;
import com.juansecu.opentoonix.shared.adapters.JwtAdapter;
import com.juansecu.opentoonix.user.daos.IUserDao;
import com.juansecu.opentoonix.user.daos.IUserInformationDao;
import com.juansecu.opentoonix.user.dtos.requests.LoginReqDto;
import com.juansecu.opentoonix.user.dtos.requests.NewUserReqDto;
import com.juansecu.opentoonix.user.dtos.responses.IsValidUsernameResDto;
import com.juansecu.opentoonix.user.dtos.responses.LoginResDto;
import com.juansecu.opentoonix.user.enums.ELoggingInError;
import com.juansecu.opentoonix.user.models.UserCookieModel;
import com.juansecu.opentoonix.user.models.UserInformationModel;
import com.juansecu.opentoonix.user.models.entities.UserEntity;
import com.juansecu.opentoonix.user.models.views.UserInformationView;
import com.juansecu.opentoonix.usernameblacklist.UsernameBlacklistService;

/**
 * Contains all data operations related to users.
 */
@Service
public class UserService {
    @Autowired
    private AvatarService avatarService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtAdapter jwtAdapter;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserInformationDao userInformationDao;
    @Autowired
    private UsernameBlacklistService usernameBlacklistService;

    /**
     * Creates a new user and their avatar.
     *
     * If the creation of avatar fails, the user data won't be saved.
     *
     * @param newUserReqDto New user's data
     */
    @Transactional
    public void createUser(NewUserReqDto newUserReqDto) {
        String userEmail = this.userDao.findEmail(newUserReqDto.getEmail().toLowerCase());

        if (userEmail == null) {
            NewAvatarReqDto newAvatarReqDto = new NewAvatarReqDto().getNewAvatarReqDtoFromString(newUserReqDto.getAvatar());
            UserEntity newUser = new UserEntity();

            try {
                newUser.setUsername(newUserReqDto.getUsername().toLowerCase());
                newUser.setEmail(newUserReqDto.getEmail().toLowerCase());
                newUser.setAge(Integer.parseInt(newUserReqDto.getAge()));
                newUser.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse(newUserReqDto.getBirthdate()));
                newUser.setGender(newUserReqDto.getGender());
                newUser.setPassword(this.bCryptPasswordEncoder.encode(newUserReqDto.getPassword()));

                newUser = this.userDao.save(newUser);

                this.avatarService.createAvatar(newAvatarReqDto, newUser);
            } catch (Exception exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                exception.printStackTrace();
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
    public IsValidUsernameResDto isValidUsername(String username) {
        IsValidUsernameResDto isValidUsernameResDto = new IsValidUsernameResDto();
        String userName = this.userDao.findUsername(username.toLowerCase());
        boolean isUsernameInBlackList = this.usernameBlacklistService.isUsernameInBlacklist(username.toLowerCase());

        if (userName == null && !isUsernameInBlackList) {
            isValidUsernameResDto.setExistsUsername(false);
            isValidUsernameResDto.setUsernameInBlackList(false);
        } else if (userName != null) isValidUsernameResDto.setExistsUsername(true);
        else if (isUsernameInBlackList) isValidUsernameResDto.setUsernameInBlackList(true);

        return isValidUsernameResDto;
    }

    /**
     * Signs in an existent user.
     *
     * @param loginReqDto The login information
     * @param request The request object
     * @return LoginResDto
     */
    public LoginResDto login(final LoginReqDto loginReqDto, final HttpServletRequest request) {
        final UserEntity user = this.userDao.findByUsername(loginReqDto.getUsername());

        if (user != null) {
            final boolean passwordMatches = bCryptPasswordEncoder.matches(
                loginReqDto.getPassword(),
                user.getPassword()
            );

            if (passwordMatches) {
                final UserInformationView userInformation = this.userInformationDao
                    .findByUsername(user.getUsername());

                return userInformation != null
                    ? new LoginResDto(
                        true,
                        null,
                        new UserInformationModel(
                            userInformation,
                            new UserCookieModel(
                                "token",
                                "/",
                                this.jwtAdapter.generateJsonWebToken(
                                    userInformation.getUsername(),
                                    request.getRequestURL().toString(),
                                    new Date(new Date().getTime() + 30 * 60 * 1000)
                                )
                            )
                        )
                    )
                    : new LoginResDto(
                            false,
                        ELoggingInError.USER_DISABLED,
                        null
                    );
            }

            return new LoginResDto(
                false,
                ELoggingInError.USER_WRONG_PASSWORD,
                null
            );
        }

        return new LoginResDto(
            false,
            ELoggingInError.USER_NOT_EXISTS,
            null
        );
    }
}
