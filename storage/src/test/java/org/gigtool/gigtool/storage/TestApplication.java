package org.gigtool.gigtool.storage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.gigtool.gigtool.storage.model")
@EnableJpaRepositories(basePackages = "org.gigtool.gigtool.storage.repositories")
@ComponentScan(basePackages = "org.gigtool.gigtool")
public class TestApplication {
}
