package ru.otus.spring.hometask02.service;

import java.util.Locale;

public interface LanguagesService {

    void chooseLocale();

    Locale getChosenLocale();
}
