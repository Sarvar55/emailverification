package com.example.emailverification.repository;

import com.example.emailverification.entity.ConfirmationToken;
import com.example.emailverification.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@Repository
public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

    ConfirmationToken findByUser_Email(String email);

    User findByUser(User user);
}
