package com.scheduleupgrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScheduleUpgradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleUpgradeApplication.class, args);
    }

}
