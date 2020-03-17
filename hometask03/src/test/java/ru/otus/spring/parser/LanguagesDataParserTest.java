package ru.otus.spring.parser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.config.Props;
import ru.otus.spring.loader.DataLoader;
import ru.otus.spring.loader.LanguagesDataLoader;
import ru.otus.spring.util.StudentsTestException;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LanguagesDataParserTest {

    @Configuration
    @EnableConfigurationProperties(Props.class)
    private static class TestConfig {

        @Bean
        LanguagesDataParser dataParser(DataLoader languagesDataLoader) {
            return new LanguagesDataParser(languagesDataLoader);
        }

    }

    @SpyBean
    LanguagesDataLoader languagesDataLoader;

    @Autowired
    LanguagesDataParser dataParser;


    @Test
    void successfulLocalesRetrievalTest() {
        Map<String, Locale> locales = dataParser.parseData();
        assertEquals(locales.get("Русский"), Locale.forLanguageTag("ru-RU"));
    }

    @Test
    void exceptionWhileLocalesRetrievalTest() {
        when(languagesDataLoader.loadData()).thenReturn(null);
        assertThrows(StudentsTestException.class, dataParser::parseData);
    }
}
