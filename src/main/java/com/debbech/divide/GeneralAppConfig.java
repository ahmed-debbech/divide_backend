package com.debbech.divide;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.debbech.divide.data")
public class GeneralAppConfig {
}
