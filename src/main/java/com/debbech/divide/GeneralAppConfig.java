package com.debbech.divide;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableJpaRepositories(basePackages = "com.debbech.divide.data")
@EnableScheduling
public class GeneralAppConfig {
}
