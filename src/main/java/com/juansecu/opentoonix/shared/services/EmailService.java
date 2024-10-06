package com.juansecu.opentoonix.shared.services;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(EmailService.class);

    @Value("${issuer.name}")
    private String issuer;
    @Value("${mail.from-account-related-email-address}")
    private String fromAccountRelatedEmailAddress;

    private final JavaMailSender mailSender;

    public boolean sendSimpleAccountManagementMessage(
        final String message,
        final String subject,
        final String to
    ) {
        return this.sendSimpleMessage(
            this.fromAccountRelatedEmailAddress,
            message,
            subject,
            to
        );
    }

    private boolean sendSimpleMessage(
        final String from,
        final String message,
        final String subject,
        final String to
    ) {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        try {
            mimeMessage.setFrom(
                this.issuer.trim() +
                    " <" +
                    from.trim() +
                    ">"
            );
            mimeMessage.setRecipients(Message.RecipientType.TO, to);
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            this.mailSender.send(mimeMessage);

            return true;
        } catch (MailSendException mailSendException) {
            EmailService.CONSOLE_LOGGER.error(
                String.format(
                    "There was an error while trying to send e-mail:%n%s",
                    mailSendException
                )
            );
        } catch (MessagingException messagingException) {
            EmailService.CONSOLE_LOGGER.error(
                String.format(
                    "There was an error while trying to set e-mail parameters:%n%s",
                    messagingException
                )
            );

        }

        return false;
    }
}
