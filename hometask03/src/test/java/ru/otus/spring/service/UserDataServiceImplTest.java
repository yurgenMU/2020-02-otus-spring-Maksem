package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.config.Props;
import ru.otus.spring.domain.User;
import ru.otus.spring.io.IOService;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UserDataServiceImplTest {

    @Configuration
    @EnableConfigurationProperties(Props.class)
    private static class TestConfig {

        @Bean
        LocalizationServiceImpl localizationService(Props props, MessageSource messageSource) {
            return new LocalizationServiceImpl(props, messageSource);
        }
    }

    @MockBean
    IOService ioService;

    @MockBean
    Props props;

    @Autowired
    UserDataServiceImpl userDataService;

    @BeforeEach
    void init() {
        doNothing().when(ioService).writeFormatted(anyString());
        when(props.getChosenLocale()).thenReturn(new Locale("en", "US"));
    }

    @Test
    void simpleTest() {
        when(ioService.read()).thenReturn("Ivan").thenReturn("Ivanov");
        assertEquals(new User("Ivan", "Ivanov"), userDataService.getUser());
    }

}
