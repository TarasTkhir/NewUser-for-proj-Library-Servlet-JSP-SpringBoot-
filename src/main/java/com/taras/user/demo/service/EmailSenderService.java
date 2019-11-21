package com.taras.user.demo.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {

    public void sendEmail(SimpleMailMessage email);
}
