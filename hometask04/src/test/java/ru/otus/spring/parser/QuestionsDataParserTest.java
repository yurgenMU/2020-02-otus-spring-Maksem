package ru.otus.spring.parser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.config.Props;
import ru.otus.spring.domain.TestData;
import ru.otus.spring.loader.DataLoader;
import ru.otus.spring.loader.QuestionsDataLoader;
import ru.otus.spring.util.StudentsTestException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class QuestionsDataParserTest {

    @Configuration
    @EnableConfigurationProperties(Props.class)
    private static class TestConfig {

        @Bean
        QuestionsDataParser dataParser(DataLoader questionsDataLoader) {
            return new QuestionsDataParser(questionsDataLoader);
        }

    }

    @Autowired
    Props props;

    @Autowired
    QuestionsDataParser dataParser;

    @SpyBean
    QuestionsDataLoader questionsDataLoader;

    @Test
    void successfulQuestionsRetrievalTest() {
        props.setQuestionsResource("questions.csv");
        TestData testData = dataParser.parseData();
        assertEquals("Pyotr Bagration", testData.getQuestions().get(4).getCorrectAnswer());
    }

    @Test
    void exceptionWhileLocalesRetrievalTest() {
        doReturn(null).when(questionsDataLoader).loadData();
        assertThrows(StudentsTestException.class, dataParser::parseData);
    }
}
