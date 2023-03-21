package com.example.emailverification.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@Document
@Data
public class ConfirmationToken {
    @Id
    private String id;
    private String confirmationToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Instant expTime;
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        this.confirmationToken = UUID.randomUUID().toString();
        this.createdDate = new Date();
        this.expTime = Instant.now().plusMillis(15000);
    }
}
