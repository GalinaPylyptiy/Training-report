package com.epam.trainingReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class TrainingReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainingReportApplication.class, args);
    }


}
