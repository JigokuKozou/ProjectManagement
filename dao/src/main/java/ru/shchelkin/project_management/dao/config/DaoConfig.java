package ru.shchelkin.project_management.dao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("ru.shchelkin.project_management.dao")
public class DaoConfig {
}
