package com.juansecu.opentoonix.users;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.juansecu.opentoonix.users.models.entities.UserEntity;
import com.juansecu.opentoonix.users.repositories.IUsersRepository;

@RequiredArgsConstructor
@Service
public class UsersApiService implements UserDetailsService {
    private final IUsersRepository usersRepository;

    public Optional<UserEntity> findById(final Integer userId) {
        return this.usersRepository.findById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.usersRepository
            .findByUsernameIgnoreCase(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
