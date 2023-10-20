package com.joi.backendProject.service;

import com.joi.backendProject.email.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {

    private final String CONFIRM_MESSAGE = "Confirm your email";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Async
    @Override
    public void send(String recipient, String email) {
        try {
            MimeMessage message = createMimeMessage(recipient, email);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Error, email not sent", e);
            throw new IllegalStateException("Failed to send the email");
        }
    }

    private MimeMessage createMimeMessage(String recipient, String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setText(email, true);
        helper.setTo(recipient);
        helper.setSubject(CONFIRM_MESSAGE);
        helper.setFrom("ozeroearosa@gmail.com");
        return message;
    }
}
