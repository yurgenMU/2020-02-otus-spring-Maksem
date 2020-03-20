package ru.otus.spring;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.executor.StudentsTestExecutor;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        StudentsTestExecutor testExecutor = context.getBean(StudentsTestExecutor.class);
        testExecutor.execute();
    }

}
