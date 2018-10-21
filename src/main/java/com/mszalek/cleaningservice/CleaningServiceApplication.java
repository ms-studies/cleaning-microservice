package com.mszalek.cleaningservice;

import com.mszalek.cleaningservice.model.CleaningLog;
import com.mszalek.cleaningservice.repository.CleaningLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@SpringBootApplication
public class CleaningServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleaningServiceApplication.class, args);
    }
}

@Component
class DummyDataCLR implements CommandLineRunner {
    @Autowired
    private CleaningLogRepository cleaningLogRepositroy;

    @Override
    public void run(String... args) throws Exception {
        Stream.of(
                new CleaningLog(100, LocalDateTime.now()),
                new CleaningLog(101, LocalDateTime.now()),
                new CleaningLog(102, LocalDateTime.now()),
                new CleaningLog(103, LocalDateTime.now()),
                new CleaningLog(104, LocalDateTime.now())
        ).forEach(log -> cleaningLogRepositroy.save(log));
    }
}

