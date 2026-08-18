package com.guru.portfolio_backend.repository;

import com.guru.portfolio_backend.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactMessage, Long> {

}