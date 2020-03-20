package ru.otus.spring.io;

public interface IOService {

    String read();

    void write(Object arg);

    void writeFormatted(String format, Object... args);
}
