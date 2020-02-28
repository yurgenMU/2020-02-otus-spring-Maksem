package ru.otus.spring.hometask01.loader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.otus.spring.hometask01.domain.Question;
import ru.otus.spring.hometask01.domain.TestData;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class TestDataLoaderImpl implements TestDataLoader {
    private static final String EMPTY = "";

    private final String questionsResource;

    public TestDataLoaderImpl(String questionsResource) {
        this.questionsResource = questionsResource;
    }

    @Override
    public TestData getTestData() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(questionsResource);
        CSVFormat csvFormat = CSVFormat.EXCEL.withFirstRecordAsHeader();
        return retrieveTestData(is, csvFormat);
    }


    private TestData retrieveTestData(InputStream inputStream, CSVFormat csvFormat) {
        try (CSVParser csvRecords = CSVParser.parse(inputStream, StandardCharsets.UTF_8, csvFormat)) {
            String description = getDescription(csvRecords);
            List<Question> questions =  StreamSupport.stream(csvRecords.spliterator(), Boolean.FALSE)
                    .map(csvRecord -> new QuestionsFactory(csvRecord).getQuestion())
                    .collect(Collectors.toList());
            return new TestData(description, questions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getDescription(CSVParser csvRecords) {
        List<String> headerNames = csvRecords.getHeaderNames();
        return headerNames.size() != 0 ? headerNames.get(0) : EMPTY;
    }

    private class QuestionsFactory {

        private Question question;

        private Question getQuestion() {
            return question;
        }

        private QuestionsFactory(CSVRecord csvRecord) {
            String questionValue = csvRecord.get(0);

            List<String> answers = IntStream.range(1, csvRecord.size())
                    .boxed()
                    .map(csvRecord::get)
                    .collect(Collectors.toList());
            question = new Question(questionValue, answers);
        }
    }
}
