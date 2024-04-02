package com.juansecu.opentoonix.mail;

/* --- Java modules --- */
import java.util.Locale;
import java.util.UUID;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


/* --- Application modules --- */
import com.juansecu.opentoonix.user.UserService;
import com.juansecu.opentoonix.user.models.entities.UserEntity;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {


    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;


    //TODO dynamic host name URL from event
    private String host = "localhost:8081";

    @Override
    public void onApplicationEvent( OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        UserEntity user = event.getUser();
        String token =  UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        //TODO language locale dynamic from event
        String confirmationUrl
            = host + "/regitrationConfirm?token=" + token;
        String message = messages.getMessage("message.regSucc", null, new Locale("en"));
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}

