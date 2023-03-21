package com.example.emailverification.service.listeners;

import com.example.emailverification.model.request.UserRegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@AllArgsConstructor
@Service
@Slf4j
public class UserRegistrationListener {

    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "q.user-registration")
    public void onUserRegistration(UserRegistrationRequest event) {
        log.info("User Registration Event Received: {}", event);
        rabbitTemplate.convertAndSend("x.post-registration", "", event);
    }

}
