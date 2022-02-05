package com.juansecu.opentoonix.usernameblacklist.daos;

/* --- Third-party modules --- */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/* --- Application modules --- */
import com.juansecu.opentoonix.usernameblacklist.models.entities.UsernameBlacklistEntity;

/**
 * Represents database operations related to username blacklist.
 */
public interface IUsernameBlacklistDao extends JpaRepository<UsernameBlacklistEntity, String> {
    /**
     * Find a currently blocked username.
     *
     * @param username The username to find
     * @return String
     */
    @Query("SELECT username FROM Username_blacklist WHERE username = ?1")
    String findOneBlockedUsername(String username);
}
