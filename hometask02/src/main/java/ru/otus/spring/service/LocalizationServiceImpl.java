package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final LanguagesService languagesService;
    private final MessageSource messageSource;

    public LocalizationServiceImpl(LanguagesService languagesService, MessageSource messageSource) {
        this.languagesService = languagesService;
        this.messageSource = messageSource;
    }

    @Override
    public String getLocalizedMessage(String key) {
        return messageSource.getMessage(key,
                null, languagesService.getChosenLocale());
    }
}
