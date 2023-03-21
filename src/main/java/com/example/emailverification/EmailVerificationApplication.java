package com.example.emailverification;

import com.example.emailverification.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoRepositories
@EnableScheduling
public class EmailVerificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailVerificationApplication.class, args);
    }

}
