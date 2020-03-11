package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.factory.IOFactory;
import ru.otus.spring.io.IOService;
import ru.otus.spring.loader.ResourceFileDataLoader;
import ru.otus.spring.service.LanguagesService;

@Configuration
public class AppConfig {
    private static final String SOURCE_PROPERTY_NAME = "questions.path";

    @Bean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/i18n/bundle");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;

    }

    @Bean
    IOFactory ioFactory() {
        return new IOFactory();
    }

    @Bean
    IOService ioService() {
        return ioFactory().getStandardIOService();
    }

    @Bean
    ResourceFileDataLoader languagesDataLoader(@Value("languages.csv") String questionsResource) {
        return new ResourceFileDataLoader(questionsResource);
    }

    @Bean
    ResourceFileDataLoader questionsDataLoader(LanguagesService languagesService) {
        languagesService.chooseLocale();
        return new ResourceFileDataLoader(messageSource().getMessage(SOURCE_PROPERTY_NAME, null, languagesService.getChosenLocale()));
    }

}
