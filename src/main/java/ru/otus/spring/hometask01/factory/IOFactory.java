package ru.otus.spring.hometask01.factory;

import ru.otus.spring.hometask01.io.IOService;
import ru.otus.spring.hometask01.io.IOServiceImpl;

public class IOFactory {

    public IOService getStandardIOService() {
        return new IOServiceImpl(System.in, System.out);
    }
}
