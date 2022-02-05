package com.juansecu.opentoonix.user;

/* --- Java modules --- */
import java.text.SimpleDateFormat;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/* --- Application modules --- */
import com.juansecu.opentoonix.avatar.AvatarService;
import com.juansecu.opentoonix.avatar.dtos.requests.NewAvatarReqDto;
import com.juansecu.opentoonix.user.daos.IUserDao;
import com.juansecu.opentoonix.user.dtos.requests.NewUserReqDto;
import com.juansecu.opentoonix.user.dtos.responses.IsValidUsernameResDto;
import com.juansecu.opentoonix.user.models.entities.UserEntity;
import com.juansecu.opentoonix.usernameblacklist.UsernameBlacklistService;

/**
 * Contains all data operations related to users.
 */
@Service
public class UserService {
    @Autowired
    private AvatarService avatarService;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private UsernameBlacklistService usernameBlacklistService;

    /**
     * Create a new user and their user.
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
                newUser.setPassword(new BCryptPasswordEncoder().encode(newUserReqDto.getPassword()));

                newUser = this.userDao.save(newUser);

                this.avatarService.createAvatar(newAvatarReqDto, newUser);
            } catch (RuntimeException runtimeException) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                runtimeException.printStackTrace();
            } catch (Exception exception) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                exception.printStackTrace();
            }
        }
    }

    /**
     * Verify if the given username is currently being used
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
}
