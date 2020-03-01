package com.salary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.salary.repository.entity"})
@EnableJpaRepositories(basePackages = "com.salary.repository.glassdor")
public class SalarystatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalarystatisticsApplication.class, args);
    }

}
