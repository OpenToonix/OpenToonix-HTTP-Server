package com.juansecu.opentoonix.usernameblacklist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.juansecu.opentoonix.usernameblacklist.repositories.IUsernameBlacklistRepository;

@RequiredArgsConstructor
@Service
public class UsernameBlacklistService {
    private final IUsernameBlacklistRepository usernameBlacklistDao;

    public boolean isUsernameInBlacklist(final String username) {
        final String usernameInBlackList = this.usernameBlacklistDao.findOneBlockedUsername(
            username
        );

        return usernameInBlackList != null;
    }
}
