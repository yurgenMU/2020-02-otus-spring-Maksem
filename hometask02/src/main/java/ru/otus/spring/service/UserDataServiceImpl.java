package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.User;
import ru.otus.spring.io.IOService;

@Service
public class UserDataServiceImpl implements UserDataService {
    private static final String NAME_PROPERTY = "questions.name";
    private static final String SURNAME_PROPERTY = "questions.surname";

    private final IOService ioService;
    private final LocalizationService localizationService;

    public UserDataServiceImpl(IOService ioService, LocalizationService localizationService) {
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public User getUser() {
        return new User(getFirstName(), getLastName());
    }

    private String getFirstName() {
        return doReadName(localizationService.getLocalizedMessage(NAME_PROPERTY));
    }

    private String getLastName() {
        return doReadName(localizationService.getLocalizedMessage(SURNAME_PROPERTY));
    }

    private String doReadName(String query) {
        ioService.write(query);
        return ioService.read().trim();
    }
}
