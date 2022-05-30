package com.cub.project.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.cub.project.repository")
@EntityScan("com.cub.project.domain.models")
@EnableJpaAuditing
public class DatabaseConfig {
}
