package ru.otus.spring.util;

public class StudentsTestException extends RuntimeException {

    public StudentsTestException(String message) {
        super(message);
    }

    public StudentsTestException(String message, Throwable cause) {
        super(message, cause);
    }

}