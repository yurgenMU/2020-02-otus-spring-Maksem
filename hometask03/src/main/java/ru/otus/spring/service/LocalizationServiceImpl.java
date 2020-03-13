package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService{

    private final SettingsService settingsService;
    private final MessageSource messageSource;

    public LocalizationServiceImpl(SettingsService settingsService, MessageSource messageSource) {
        this.settingsService = settingsService;
        this.messageSource = messageSource;
    }

    @Override
    public String getLocalizedMessage(String key, Locale... locale) {
        Locale currentLocale;
        if (locale.length == 0) {
            currentLocale = settingsService.getChosenLocale();
        } else {
            currentLocale = locale[0];
        }
        return messageSource.getMessage(key,
                null, currentLocale);
    }
}
