package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.io.IOService;
import ru.otus.spring.io.IOServiceImpl;
import ru.otus.spring.loader.ResourceFileDataLoader;
import ru.otus.spring.service.SettingsService;

@PropertySource("classpath:application.properties")
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
    IOService ioService() {
        return new IOServiceImpl(System.in, System.out);
    }

    @Bean
    ResourceFileDataLoader languagesDataLoader(@Value("${languages.file}") String questionsResource) {
        return new ResourceFileDataLoader(questionsResource);
    }

    @Bean
    ResourceFileDataLoader questionsDataLoader(SettingsService settingsService) {
        return new ResourceFileDataLoader(settingsService);
    }

}
