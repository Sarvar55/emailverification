package com.example.emailverification.service.scheduled;

import com.example.emailverification.entity.ConfirmationToken;
import com.example.emailverification.repository.ConfirmationTokenRepository;
import com.example.emailverification.service.rules.CheckIfExpiredToken;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@Service
@AllArgsConstructor
@EnableScheduling
public class ExpiredTokenCleaner {

    private ConfirmationTokenRepository confirmationTokenRepository;
    private CheckIfExpiredToken checkIfExpiredToken;

    @Scheduled(fixedRate = 20000)
    public void deleteTokens() {
        List<ConfirmationToken> tokens = confirmationTokenRepository.findAll();
        tokens.stream().filter(confirmationToken -> !checkIfExpiredToken.isTokenExpired(confirmationToken))
                .forEach(expiredToken -> {
                    confirmationTokenRepository.delete(expiredToken);
                });
    }

}
