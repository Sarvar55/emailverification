package com.example.emailverification.controller;

import com.example.emailverification.entity.ConfirmationToken;
import com.example.emailverification.entity.User;
import com.example.emailverification.model.request.UserRegistrationRequest;
import com.example.emailverification.repository.ConfirmationTokenRepository;
import com.example.emailverification.repository.UserRepository;
import com.example.emailverification.service.rules.CheckIfEmailExists;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private RabbitTemplate rabbitTemplate;
    private UserRepository userRepository;
    private ConfirmationTokenRepository confirmationTokenRepository;
    private CheckIfEmailExists checkIfEmailExists;

    @PostMapping("/users")
    public String saveuser(@RequestBody UserRegistrationRequest request) {
        if (checkIfEmailExists.emailExist(request.getEmail()))
            return "bu email zaten kullanılıyor";

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setEnable(false);
        user.setPassword(request.getPassword());
        userRepository.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        rabbitTemplate.convertAndSend("", "q.user-registration", request);
        return "Verify email by the link sent on your email address";
    }
}
