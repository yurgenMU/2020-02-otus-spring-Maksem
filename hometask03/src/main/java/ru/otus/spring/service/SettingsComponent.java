package ru.otus.spring.service;

import org.springframework.stereotype.Component;
import ru.otus.spring.config.Props;

@Component
public class SettingsComponent {

    private final String questionsPathTemplate;
    private final String defaultLocaleLanguage;
    private final String defaultLocaleCountry;

    public SettingsComponent(Props props) {
        this.questionsPathTemplate = props.getQuestionsFileTemplate();
        this.defaultLocaleLanguage = props.getDefaultLocaleLanguage();
        this.defaultLocaleCountry = props.getDefaultLocaleCountry();
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
