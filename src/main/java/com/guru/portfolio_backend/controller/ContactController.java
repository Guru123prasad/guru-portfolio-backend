package com.guru.portfolio_backend.controller;

import com.guru.portfolio_backend.EmailService;
import com.guru.portfolio_backend.entity.ContactMessage;
import com.guru.portfolio_backend.repository.ContactRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactRepository contactRepository;
    private final EmailService emailService;

    public ContactController(
            ContactRepository contactRepository,
            EmailService emailService) {

        this.contactRepository = contactRepository;
        this.emailService = emailService;
    }

    @PostMapping
    public ContactMessage saveMessage(
            @RequestBody ContactMessage message) {

        // 1. Save the message to Aiven MySQL
        ContactMessage savedMessage = contactRepository.save(message);

        // 2. Send the message to your email
        emailService.sendContactEmail(savedMessage);

        // 3. Return the saved message
        return savedMessage;
    }
}