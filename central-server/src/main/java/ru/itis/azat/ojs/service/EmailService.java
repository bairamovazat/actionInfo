package ru.itis.azat.ojs.service;

public interface EmailService {
    void sendMail(String text, String subject, String email);

    void sendMailAsync(String text, String subject, String email);
}
