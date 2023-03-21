package com.example.emailverification.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@Document
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private boolean isEnable;
}
