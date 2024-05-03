package com.juansecu.opentoonix.usernameblacklist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.juansecu.opentoonix.usernameblacklist.models.entities.UsernameBlacklistEntity;

@Repository
public interface IUsernameBlacklistRepository extends JpaRepository<UsernameBlacklistEntity, String> {
    @Query("SELECT username FROM Username_blacklist WHERE username LIKE (?1)")
    String findOneBlockedUsername(String username);
}
