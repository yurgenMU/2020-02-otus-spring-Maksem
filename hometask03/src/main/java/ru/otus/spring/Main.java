package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.executor.StudentsTestExecutor;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        StudentsTestExecutor testExecutor = context.getBean(StudentsTestExecutor.class);
        testExecutor.execute();
    }
}
