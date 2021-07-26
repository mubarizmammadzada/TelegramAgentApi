package com.example.telegramagentapi.services;

public interface EmailService {
    void sendMail(String email, String subject, String text);
}
