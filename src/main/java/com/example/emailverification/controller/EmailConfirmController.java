package com.example.emailverification.controller;

import com.example.emailverification.entity.ConfirmationToken;
import com.example.emailverification.entity.User;
import com.example.emailverification.repository.ConfirmationTokenRepository;
import com.example.emailverification.repository.UserRepository;
import com.example.emailverification.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@Slf4j
@RestController
@AllArgsConstructor
public class EmailConfirmController {

    private ConfirmationTokenService confirmationTokenService;

    @GetMapping("/confirim-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        if (confirmationTokenService.verifyEmail(confirmationToken))
            return ResponseEntity.ok("Email verified successfully!");
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }
}
