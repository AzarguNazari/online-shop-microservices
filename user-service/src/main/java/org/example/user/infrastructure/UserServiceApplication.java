package org.example.user.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "org.example.user")
@EnableMongoRepositories(basePackages = "org.example.user.infrastructure.adapter.persistence")
@EntityScan(basePackages = "org.example.user.infrastructure.adapter.persistence")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
