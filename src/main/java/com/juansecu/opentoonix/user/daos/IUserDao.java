package com.juansecu.opentoonix.user.daos;

/* --- Third-party modules --- */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.models.entities.UserEntity;

/**
 * Represents database operations related to users.
 */
public interface IUserDao extends JpaRepository<UserEntity, String> {
    /**
     * Find a email being used.
     *
     * @param email The email to find
     * @return String
     */
    @Query("SELECT email FROM Users WHERE email = ?1")
    String findEmail(String email);

    /**
     * Find a username being used.
     *
     * @param username The username to find
     * @return String
     */
    @Query("SELECT username FROM Users WHERE username = ?1")
    String findUsername(String username);
}
