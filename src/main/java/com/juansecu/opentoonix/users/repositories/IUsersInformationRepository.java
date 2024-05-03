package com.juansecu.opentoonix.users.repositories;

import org.springframework.data.repository.Repository;

import com.juansecu.opentoonix.users.models.views.UserInformationView;

@org.springframework.stereotype.Repository
public interface IUsersInformationRepository
    extends Repository<UserInformationView, Integer> {
    UserInformationView findByUsernameIgnoreCase(final String username);
}
