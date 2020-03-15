package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SettingsComponent {

    private final String questionsPathTemplate;
    private final String defaultLocaleLanguage;
    private final String defaultLocaleCountry;

    public SettingsComponent(@Value("${questions.file.template}") String questionsPathTemplate,
                             @Value("${default.locale.language}") String defaultLocaleLanguage,
                             @Value("${default.locale.country}") String defaultLocaleCountry) {
        this.questionsPathTemplate = questionsPathTemplate;
        this.defaultLocaleLanguage = defaultLocaleLanguage;
        this.defaultLocaleCountry = defaultLocaleCountry;
    }

    public String getQuestionsPathTemplate() {
        return questionsPathTemplate;
    }

    public String getDefaultLocaleLanguage() {
        return defaultLocaleLanguage;
    }

    public String getDefaultLocaleCountry() {
        return defaultLocaleCountry;
    }
}
