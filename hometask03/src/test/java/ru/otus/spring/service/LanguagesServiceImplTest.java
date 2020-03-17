package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.config.Props;
import ru.otus.spring.io.IOService;
import ru.otus.spring.loader.LanguagesDataLoader;
import ru.otus.spring.parser.LanguagesDataParser;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LanguagesServiceImplTest {

    @Configuration
    @EnableConfigurationProperties(Props.class)
    private static class TestConfig {

        @Bean
        LanguagesDataLoader languagesDataLoader(Props props) {
            return new LanguagesDataLoader(props);
        }

        @Bean
        LanguagesDataParser languagesDataParser(LanguagesDataLoader languagesDataLoader) {
            return new LanguagesDataParser(languagesDataLoader);
        }

        @Bean
        LanguagesServiceImpl languagesService(IOService ioService, LanguagesDataParser languagesDataParser,
                                              Props props) {
            return new LanguagesServiceImpl(ioService, languagesDataParser, props);
        }
    }

    @MockBean
    IOService ioService;

    @Autowired
    LanguagesServiceImpl languagesService;

    @Autowired
    Props props;

    @BeforeEach
    void init() {
        doNothing().when(ioService).writeFormatted(anyString());
    }

    @Test
    void validLocaleIndexTest() {
        when(ioService.read()).thenReturn("2");
        languagesService.chooseLocale();
        assertEquals(new Locale("ru", "RU"), props.getChosenLocale());
    }

    @Test
    void invalidLocaleIndexTest() {
        when(ioService.read()).thenReturn("5");
        languagesService.chooseLocale();
        assertEquals(new Locale("en", "US"), props.getChosenLocale());
    }

    @Test
    void nonNumericLocaleIndexTest() {
        when(ioService.read()).thenReturn("index");
        languagesService.chooseLocale();
        assertEquals(new Locale("en", "US"), props.getChosenLocale());
    }
}
