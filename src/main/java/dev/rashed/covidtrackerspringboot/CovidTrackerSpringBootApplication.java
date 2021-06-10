package dev.rashed.covidtrackerspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CovidTrackerSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CovidTrackerSpringBootApplication.class, args);
    }

}
