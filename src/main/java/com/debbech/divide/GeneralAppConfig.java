package com.debbech.divide;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableJpaRepositories(basePackages = "com.debbech.divide.data")
@EnableScheduling
public class GeneralAppConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(3); // Configure the pool size as needed
        scheduler.setThreadNamePrefix("ScheduledTask-");
        return scheduler;
    }
}
