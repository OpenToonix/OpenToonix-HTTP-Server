package com.juansecu.opentoonix.mail;

/* --- Java modules --- */
import java.util.Locale;
import java.util.UUID;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.UserService;
import com.juansecu.opentoonix.user.models.entities.UserEntity;
import com.juansecu.opentoonix.shared.utils.mail.EmailUtil;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messages;
    @Autowired
    private EmailUtil emailUtil;

    //TODO dynamic host name URL from event
    private String host = "localhost:8081";

    @Override
    public void onApplicationEvent( OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        UserEntity user = event.getUser();
        String token =  UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        //TODO language locale dynamic from event
        String confirmationUrl
            = host + "/user/registrationConfirm?token=" + token;
        String message = messages.getMessage("message.regSucc", null, new Locale("en"));
        emailUtil.sendEmail(recipientAddress, subject, message + "\r\n" + "http://localhost:8080" + confirmationUrl);
    }
}