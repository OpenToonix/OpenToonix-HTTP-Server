package com.juansecu.opentoonix.mail;

/* --- Java modules --- */
import java.util.Locale;
import java.util.UUID;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${APPLICATION_HOST}")
    private String host;
    @Value("${APPLICATION_PORT}")
    private String port;
    @Value("${ACCOUNTS_FROM_EMAIL_ADDRESS}")
    private String fromEmailAddress;
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
        String confirmationUrl
            = host +":"+ port + "/user/registrationConfirm?token=" + token;
        String message = messages.getMessage("message.regSucc", null, new Locale("en"));
        emailUtil.sendEmail(recipientAddress, subject, message + "\r\n"  + confirmationUrl, fromEmailAddress);
    }
}