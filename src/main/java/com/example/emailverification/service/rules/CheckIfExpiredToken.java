package com.example.emailverification.service.rules;

import com.example.emailverification.entity.ConfirmationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@Service
public class CheckIfExpiredToken {

    public boolean isTokenExpired(ConfirmationToken confirmationToken) {
        return !confirmationToken.getExpTime().isBefore(Instant.now());
    }

}
