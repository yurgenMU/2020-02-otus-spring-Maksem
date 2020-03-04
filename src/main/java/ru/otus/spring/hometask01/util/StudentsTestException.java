package ru.otus.spring.hometask01.util;

public class StudentsTestException extends RuntimeException {

    public StudentsTestException(String message) {
        super(message);
    }

    public StudentsTestException(String message, Throwable cause) {
        super(message, cause);
    }

}