package com.juansecu.opentoonix.usernameblacklist;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* --- Application modules --- */
import com.juansecu.opentoonix.usernameblacklist.daos.IUsernameBlacklistDao;

@Service
public class UsernameBlacklistService {
    @Autowired
    private IUsernameBlacklistDao usernameBlacklistDao;

    /**
     * Check if the given username is part of the username blacklist or not.
     *
     * @param username The username to verify
     * @return boolean
     */
    public boolean isUsernameInBlacklist(String username) {
        String usernameInBlackList = this.usernameBlacklistDao.findOneBlockedUsername(username);
        return usernameInBlackList != null;
    }
}
