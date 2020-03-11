package ru.otus.spring.factory;

import ru.otus.spring.io.IOService;
import ru.otus.spring.io.IOServiceImpl;

public class IOFactory {

    public IOService getStandardIOService() {
        return new IOServiceImpl(System.in, System.out);
    }
}
