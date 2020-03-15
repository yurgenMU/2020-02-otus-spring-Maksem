package ru.otus.spring.parser;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.loader.LanguagesDataLoader;
import ru.otus.spring.util.StudentsTestException;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LanguagesDataParserTest {

//    @Test
//    void successfulLocalesRetrievalTest() {
//        LanguagesDataLoader dataLoader = new LanguagesDataLoader("languages.csv");
//        LanguagesDataParser dataParser = new LanguagesDataParser(dataLoader);
//        Map<String, Locale> locales = dataParser.parseData();
//        assertEquals(locales.get("Русский"), Locale.forLanguageTag("ru-RU"));
//    }
//
//    @Test
//    void exceptionWhileLocalesRetrievalTest() {
//        LanguagesDataLoader dataLoader = mock(LanguagesDataLoader.class);
//        when(dataLoader.loadData()).thenReturn(null);
//        LanguagesDataParser dataParser = new LanguagesDataParser(dataLoader);
//
//        assertThrows(StudentsTestException.class, dataParser::parseData);
//    }
}
