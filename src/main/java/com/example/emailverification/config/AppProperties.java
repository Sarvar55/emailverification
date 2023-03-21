package com.example.emailverification.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

/**
 * @project: emailVerification
 * @author: Sarvar55
 */
@ConfigurationProperties(prefix = "app")
@Validated
public record AppProperties(String username, String password, String domain) {

    @ConstructorBinding
    public AppProperties(String username, String password, String domain) {
        this.username = username;
        this.domain = domain;
        this.password = password;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String domain() {
        return domain;
    }
}
