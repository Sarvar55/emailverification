package com.example.emailverification.service.listeners;

import com.example.emailverification.model.request.UserRegistrationRequest;
import com.example.emailverification.service.notfications.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@AllArgsConstructor
@Service
@Slf4j
public class SendEmailListener {

    private EmailService emailService;

    @RabbitListener(queues = "q.send-email")
    public void sendMessage(UserRegistrationRequest request) {
        log.info("Sending email to {}", request.getEmail());
        try {
            emailService.sendMessage(request.getEmail());
        } catch (Exception exception) {
            log.info("Exception when send email:{}", exception.getMessage());
        }
    }
}
