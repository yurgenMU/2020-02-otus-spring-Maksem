package ru.otus.spring.hometask01;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.hometask01.executor.StudentsTestExecutor;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        StudentsTestExecutor testExecutor = context.getBean(StudentsTestExecutor.class);
        testExecutor.execute();
    }

}
