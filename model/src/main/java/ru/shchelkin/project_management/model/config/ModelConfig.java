package ru.shchelkin.project_management.model.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("ru.shchelkin.project_management.model")
public class ModelConfig {
}
