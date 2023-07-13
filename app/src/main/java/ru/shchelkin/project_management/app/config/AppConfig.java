package ru.shchelkin.project_management.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.shchelkin.project_management.business.config.BusinessConfig;
import ru.shchelkin.project_management.controller.config.ControllerConfig;
import ru.shchelkin.project_management.dao.config.DaoConfig;
import ru.shchelkin.project_management.model.config.ModelConfig;

@Configuration
@Import({ModelConfig.class, DaoConfig.class, BusinessConfig.class, ControllerConfig.class})
public class AppConfig {
}
