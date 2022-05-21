package com.juansecu.opentoonix.user.daos;

/* --- Third-party modules --- */
import org.springframework.data.repository.Repository;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.models.views.UserInformationView;

/**
 * Represents database operations related to V_User_information.
 */
public interface IUserInformationDao extends Repository<UserInformationView, Integer> {
    /**
     * Returns the information from the user,
     * including their avatar's information.
     *
     * @param username The username to retrieve information
     * @return UserInformationView
     */
    UserInformationView findByUsername(String username);
}
