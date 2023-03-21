package com.example.emailverification.service.rules;

import com.example.emailverification.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@Service
@AllArgsConstructor
public class CheckIfEmailExists {
    private UserRepository userRepository;

    public boolean emailExist(String email) {
        return userRepository.existsByEmail(email);
    }
}
