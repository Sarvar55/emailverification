package com.example.emailverification.service.notfications;

import com.example.emailverification.entity.ConfirmationToken;
import com.example.emailverification.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@AllArgsConstructor
@Service
public class EmailService implements MessageService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    private JavaMailSender mailSender;

    @Override
    public void sendMessage(String message) {
        mailSender.send(message(message));
    }

    public SimpleMailMessage message(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByUser_Email(email);
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : " + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
        return mailMessage;
    }

}
