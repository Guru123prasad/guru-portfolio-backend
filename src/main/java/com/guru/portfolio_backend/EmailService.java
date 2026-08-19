package com.guru.portfolio_backend;

import com.guru.portfolio_backend.entity.ContactMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactEmail(ContactMessage message) {

        try {

            SimpleMailMessage email = new SimpleMailMessage();

            email.setTo(System.getenv("MAIL_TO"));

            email.setSubject(
                    "New Portfolio Contact - " + message.getName()
            );

            email.setText(
                    "You received a new message from your portfolio.\n\n" +
                    "Name: " + message.getName() + "\n" +
                    "Email: " + message.getEmail() + "\n" +
                    "Business: " + message.getBusiness() + "\n\n" +
                    "Message:\n" +
                    message.getMessage()
            );

            email.setFrom(System.getenv("MAIL_USERNAME"));

            mailSender.send(email);

            System.out.println("EMAIL SENT SUCCESSFULLY");

        } catch (Exception e) {

            System.out.println("EMAIL SENDING FAILED");
            e.printStackTrace();
        }
    }
}