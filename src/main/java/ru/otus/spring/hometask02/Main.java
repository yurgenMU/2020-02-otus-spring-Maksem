package ru.otus.spring.hometask02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.hometask02.executor.StudentsTestExecutor;

@ComponentScan
public class Main {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();
        StudentsTestExecutor testExecutor = context.getBean(StudentsTestExecutor.class);
        testExecutor.execute();
    }

}
