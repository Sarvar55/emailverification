package com.example.emailverification.service;

import com.example.emailverification.entity.ConfirmationToken;
import com.example.emailverification.entity.User;
import com.example.emailverification.repository.ConfirmationTokenRepository;
import com.example.emailverification.repository.UserRepository;
import com.example.emailverification.service.rules.CheckIfExpiredToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@AllArgsConstructor
@Service
@Slf4j
public class ConfirmationTokenService {

    private CheckIfExpiredToken checkIfExpiredToken;
    private ConfirmationTokenRepository confirmationTokenRepository;
    private UserRepository userRepository;

    public boolean verifyEmail(String confirmationToken) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token == null)
            return false;

        log.info("token=" + token.getConfirmationToken());
        if (!checkIfExpiredToken.isTokenExpired(token))
            return false;


        User user = userRepository.findByEmail(token.getUser().getEmail());
        user.setEnable(true);
        userRepository.save(user);
        confirmationTokenRepository.delete(token);
        return true;
    }

}
