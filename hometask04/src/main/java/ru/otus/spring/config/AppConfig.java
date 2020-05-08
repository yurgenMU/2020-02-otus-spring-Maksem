package ru.otus.spring.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.io.IOService;
import ru.otus.spring.io.IOServiceImpl;


@Configuration
@EnableConfigurationProperties(Props.class)
public class AppConfig {

    @Bean
    IOService ioService() {
        return new IOServiceImpl(System.in, System.out);
    }
}
