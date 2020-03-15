package ru.otus.spring.service;

import java.util.Locale;

public interface LanguagesService {

    void chooseLocale();

    Locale getChosenLocale();

    String getQuestionsResource();

}
