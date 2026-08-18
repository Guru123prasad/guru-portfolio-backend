package com.guru.portfolio_backend.controller;

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

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping
    public ContactMessage saveMessage(
            @RequestBody ContactMessage message) {

        return contactRepository.save(message);
    }
}