package ru.otus.spring.hometask02.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.hometask02.domain.User;
import ru.otus.spring.hometask02.io.IOService;

@Service
public class UserDataServiceImpl implements UserDataService {
    private static final String NAME_PROPERTY = "questions.name";
    private static final String SURNAME_PROPERTY = "questions.surname";

    private final IOService ioService;
    private final LanguagesService languagesService;
    private final MessageSource messageSource;

    public UserDataServiceImpl(IOService ioService, LanguagesService languagesService, MessageSource messageSource) {
        this.ioService = ioService;
        this.languagesService = languagesService;
        this.messageSource = messageSource;
    }

    @Override
    public User getUser() {
        return new User(getFirstName(), getLastName());
    }

    private String getFirstName() {
        return doReadName(messageSource.getMessage(NAME_PROPERTY, null, languagesService.getChosenLocale()));
    }

    private String getLastName() {
        return doReadName(messageSource.getMessage(SURNAME_PROPERTY, null, languagesService.getChosenLocale()));
    }

    private String doReadName(String query) {
        ioService.write(query);
        return ioService.read().trim();
    }


}
