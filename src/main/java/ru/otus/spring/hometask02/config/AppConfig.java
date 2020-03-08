package ru.otus.spring.hometask02.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.hometask02.factory.IOFactory;
import ru.otus.spring.hometask02.io.IOService;
import ru.otus.spring.hometask02.loader.ResourceFileDataLoader;
import ru.otus.spring.hometask02.parser.LanguagesDataParser;
import ru.otus.spring.hometask02.service.LanguagesService;
import ru.otus.spring.hometask02.service.LanguagesServiceImpl;
import ru.otus.spring.hometask02.service.QuestionsProcessorImpl;
import ru.otus.spring.hometask02.service.UserDataServiceImpl;


@Configuration
public class AppConfig {

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
    ResourceFileDataLoader dataLoader(@Value("languages.csv") String questionsResource) {
        return new ResourceFileDataLoader(questionsResource);
    }

    @Bean
    LanguagesDataParser languagesDataParser(ResourceFileDataLoader dataLoader) {
        return new LanguagesDataParser(dataLoader);
    }


    @Bean
    LanguagesServiceImpl languageService(IOService ioService, LanguagesDataParser languagesDataParser) {
        return new LanguagesServiceImpl(ioService, languagesDataParser);
    }

    @Bean
    UserDataServiceImpl userDataService(IOService ioService, LanguagesService languagesService, MessageSource messageSource) {
        return new UserDataServiceImpl(ioService, languagesService, messageSource);
    }

    @Bean
    QuestionsProcessorImpl questionsProcessor(IOService ioService, MessageSource messageSource, LanguagesService languagesService) {
        return new QuestionsProcessorImpl(ioService, messageSource, languagesService);
    }


}
