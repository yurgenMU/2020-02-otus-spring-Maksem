package ru.otus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class Props {

    private String languagesFile;
    private String questionsFileTemplate;
    private Locale chosenLocale;
    private String questionsFileThreshold;
    private String defaultLocaleLanguage;
    private String defaultLocaleCountry;


    public String getLanguagesFile() {
        return languagesFile;
    }

    public void setLanguagesFile(String languagesFile) {
        this.languagesFile = languagesFile;
    }

    public String getQuestionsFileTemplate() {
        return questionsFileTemplate;
    }

    public void setQuestionsFileTemplate(String questionsFileTemplate) {
        this.questionsFileTemplate = questionsFileTemplate;
    }

    public String getQuestionsFileThreshold() {
        return questionsFileThreshold;
    }

    public void setQuestionsFileThreshold(String questionsFileThreshold) {
        this.questionsFileThreshold = questionsFileThreshold;
    }

    public String getDefaultLocaleLanguage() {
        return defaultLocaleLanguage;
    }

    public void setDefaultLocaleLanguage(String defaultLocaleLanguage) {
        this.defaultLocaleLanguage = defaultLocaleLanguage;
    }

    public String getDefaultLocaleCountry() {
        return defaultLocaleCountry;
    }

    public void setDefaultLocaleCountry(String defaultLocaleCountry) {
        this.defaultLocaleCountry = defaultLocaleCountry;
    }

    public Locale getChosenLocale() {
        return chosenLocale;
    }

    public void setChosenLocale(Locale chosenLocale) {
        this.chosenLocale = chosenLocale;
    }
}
