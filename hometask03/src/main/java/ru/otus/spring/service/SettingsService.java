package ru.otus.spring.service;

import java.util.Locale;

public interface SettingsService {

    void chooseLocale();

    Locale getChosenLocale();

    String getQuestionsResource();

}
