package ru.otus.spring.hometask01.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.hometask01.domain.TestData;
import ru.otus.spring.hometask01.loader.DataLoader;
import ru.otus.spring.hometask01.loader.ResourceFileDataLoader;
import ru.otus.spring.hometask01.util.StudentsTestException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataParserImplTest {
    private static final String QUESTIONS_RESOURCE = "questions.csv";
    private static final String SAMPLE_DESCRIPTION = "Test Sample";
    private static final String CORRECT_ANSWER = "Moscow";


    @Test
    void successfulParsingTest() {
        ResourceFileDataLoader dataLoader = new ResourceFileDataLoader(QUESTIONS_RESOURCE);
        DataParserImpl dataParser = new DataParserImpl(dataLoader);
        TestData testData = dataParser.getTestData();
        assertEquals(testData.getDescription(), SAMPLE_DESCRIPTION);
        assertEquals(testData.getQuestions().get(0).getCorrectAnswer(), CORRECT_ANSWER);
    }

    @Test
    void nonexistentResourceTest() {
        DataLoader dataLoader = mock(DataLoader.class);
        when(dataLoader.loadData()).thenReturn(null);
        DataParserImpl dataParser = new DataParserImpl(dataLoader);

        assertThrows(StudentsTestException.class, dataParser::getTestData);
    }
}
