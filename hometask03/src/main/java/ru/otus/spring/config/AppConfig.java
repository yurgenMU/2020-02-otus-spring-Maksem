package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(YamlProps.class)
public class AppConfig {
    private static final String SOURCE_PROPERTY_NAME = "questions.path";

    @Bean
    @ConditionalOnMissingBean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/i18n/bundle");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    @ConditionalOnMissingBean
    IOService ioService() {
        return new IOServiceImpl(System.in, System.out);
    }

    @Bean
    @ConditionalOnMissingBean
    ResourceFileDataLoader languagesDataLoader(@Value("${languages.file}") String questionsResource) {
        return new ResourceFileDataLoader(questionsResource);
    }

    @Bean
    @ConditionalOnMissingBean
    ResourceFileDataLoader questionsDataLoader(SettingsService settingsService) {
        return new ResourceFileDataLoader(settingsService);
    }

}
