package ru.otus.spring.hometask02.factory;

import ru.otus.spring.hometask02.io.IOService;
import ru.otus.spring.hometask02.io.IOServiceImpl;

public class IOFactory {

    public IOService getStandardIOService() {
        return new IOServiceImpl(System.in, System.out);
    }
}
