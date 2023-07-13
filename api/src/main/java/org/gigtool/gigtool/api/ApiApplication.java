package org.gigtool.gigtool.api;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.gigtool.gigtool.storage.model")
@EnableJpaRepositories(basePackages = "org.gigtool.gigtool.storage.repositories")
@ComponentScan(basePackages = "org.gigtool.gigtool")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
