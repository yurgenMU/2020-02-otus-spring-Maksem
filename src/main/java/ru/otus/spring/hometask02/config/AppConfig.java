package ru.otus.spring.hometask02.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.hometask02.factory.IOFactory;
import ru.otus.spring.hometask02.io.IOService;
import ru.otus.spring.hometask02.loader.ResourceFileDataLoader;
import ru.otus.spring.hometask02.service.QuestionsProcessorImpl;
import ru.otus.spring.hometask02.service.UserDataServiceImpl;


@Configuration
public class AppConfig {

    @Bean
    ResourceFileDataLoader dataLoader(@Value("questions.csv") String questionsResource) {
        return new ResourceFileDataLoader(questionsResource);
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
    UserDataServiceImpl userDataService(IOService ioService) {
        return new UserDataServiceImpl(ioService);
    }

    @Bean
    QuestionsProcessorImpl questionsProcessor(IOService ioService) {
        return new QuestionsProcessorImpl(ioService);
    }

}
