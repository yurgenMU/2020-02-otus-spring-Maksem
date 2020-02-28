package ru.otus.spring.hometask01.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.spring.hometask01.domain.TestData;
import ru.otus.spring.hometask01.loader.DataLoader;
import ru.otus.spring.hometask01.loader.DataLoaderImpl;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataParserImplTest {
    private static final String QUESTIONS_RESOURCE = "questions.csv";
    private static final String SAMPLE_DESCRIPTION = "Test Sample";
    private static final String CORRECT_ANSWER = "Moscow";


    @Test
    public void successfulParsingTest() {
        DataLoaderImpl dataLoader = new DataLoaderImpl(QUESTIONS_RESOURCE);
        DataParserImpl dataParser = new DataParserImpl(dataLoader);
        TestData testData = dataParser.getTestData();
        Assert.assertEquals(testData.getDescription(), SAMPLE_DESCRIPTION);
        Assert.assertEquals(testData.getQuestions().get(0).getCorrectAnswer(), CORRECT_ANSWER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonexistentResourceTest() {
        DataLoader dataLoader = mock(DataLoader.class);
        when(dataLoader.loadData()).thenReturn(null);
        DataParserImpl dataParser = new DataParserImpl(dataLoader);
        dataParser.getTestData();
    }
}
