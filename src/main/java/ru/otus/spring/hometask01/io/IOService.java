package ru.otus.spring.hometask01.io;

public interface IOService {

    String read();

    void write(Object arg);

    void writeFormatted(String format, Object... args);
}
