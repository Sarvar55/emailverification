package com.example.emailverification.model.request;

import lombok.Data;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@Data
public class UserRegistrationRequest {

    private String username;

    private String email;

    private String password;
}
