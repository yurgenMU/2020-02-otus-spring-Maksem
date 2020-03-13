package ru.otus.spring.service;

import java.util.Locale;

public interface LocalizationService {

    String getLocalizedMessage(String message, Locale... locale);
}
