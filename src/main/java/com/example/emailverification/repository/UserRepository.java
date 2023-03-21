package com.example.emailverification.repository;

import com.example.emailverification.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);

    Boolean existsByEmail(String email);

}
