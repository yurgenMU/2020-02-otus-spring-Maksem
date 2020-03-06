package ru.otus.spring.hometask02.io;

public interface IOService {

    String read();

    void write(Object arg);

    void writeFormatted(String format, Object... args);
}
