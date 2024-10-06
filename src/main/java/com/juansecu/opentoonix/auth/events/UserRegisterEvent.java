package com.juansecu.opentoonix.auth.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import com.juansecu.opentoonix.users.models.entities.UserEntity;

@Getter
public class UserRegisterEvent extends ApplicationEvent {
    private final UserEntity user;

    public UserRegisterEvent(UserEntity user) {
        super(user);
        this.user = user;
    }
}
