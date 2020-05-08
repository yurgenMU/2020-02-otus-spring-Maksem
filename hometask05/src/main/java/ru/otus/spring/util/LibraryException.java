package ru.otus.spring.util;

public class LibraryException extends RuntimeException {

    public LibraryException(String message) {
        super(message);
    }

    public LibraryException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
