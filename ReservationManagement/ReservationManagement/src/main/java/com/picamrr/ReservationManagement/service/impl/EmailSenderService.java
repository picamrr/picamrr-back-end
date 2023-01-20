package com.picamrr.ReservationManagement.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    @Value("${spring.mail.username}")
    private String SENDER;
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER);
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }
}
