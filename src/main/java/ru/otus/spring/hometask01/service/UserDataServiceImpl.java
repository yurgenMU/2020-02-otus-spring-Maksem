package ru.otus.spring.hometask01.service;

import ru.otus.spring.hometask01.io.IOService;

public class UserDataServiceImpl implements UserDataService {
    private static final String NAME_QUERY = "Enter your name";
    private static final String SURNAME_QUERY = "Enter your surname";

    private IOService ioService;

    public UserDataServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public String getFirstName() {
        return doReadName(NAME_QUERY);
    }

    @Override
    public String getLastName() {
        return doReadName(SURNAME_QUERY);
    }

    private String doReadName(String query) {
        ioService.write(query);
        return ioService.read().trim();
    }
}
