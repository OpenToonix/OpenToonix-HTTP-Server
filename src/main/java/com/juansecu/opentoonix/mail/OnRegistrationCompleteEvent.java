package com.juansecu.opentoonix.mail;

/* --- Third-party modules --- */
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.models.entities.UserEntity;

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private UserEntity user;

    public OnRegistrationCompleteEvent(UserEntity user) {
        super(user);
        this.user = user;
    }

}
