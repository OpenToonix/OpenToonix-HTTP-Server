package com.juansecu.opentoonix.users.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.juansecu.opentoonix.users.models.entities.UserEntity;

@Repository
public interface IUsersRepository extends JpaRepository<UserEntity, Integer> {
    @Query("SELECT email FROM Users WHERE email LIKE (?1)")
    String findEmail(final String email);

    @Query("SELECT username FROM Users WHERE username LIKE (?1)")
    String findUsername(final String username);

    Optional<UserEntity> findByUsernameIgnoreCase(final String username);
}
