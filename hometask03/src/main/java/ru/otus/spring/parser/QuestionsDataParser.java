package ru.otus.spring.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestData;
import ru.otus.spring.loader.DataLoader;
import ru.otus.spring.util.StudentsTestException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
public class QuestionsDataParser implements DataParser<TestData> {
    private static final String EMPTY = "";

    private final DataLoader questionsDataLoader;

    QuestionsDataParser(DataLoader questionsDataLoader) {
        this.questionsDataLoader = questionsDataLoader;
    }

    @Override
    public TestData parseData() {
        CSVFormat csvFormat = CSVFormat.EXCEL.withFirstRecordAsHeader();
        return retrieveTestData(questionsDataLoader.loadData(), csvFormat);
    }


    private TestData retrieveTestData(InputStream inputStream, CSVFormat csvFormat) {
        try (CSVParser csvRecords = CSVParser.parse(inputStream, StandardCharsets.UTF_8, csvFormat)) {
            String description = getDescription(csvRecords);
            List<Question> questions = StreamSupport.stream(csvRecords.spliterator(), Boolean.FALSE)
                    .map(this::createQuestion)
                    .collect(Collectors.toList());
            return new TestData(description, questions);
        } catch (Exception e) {
            throw new StudentsTestException("Error while parsing data", e);
        }
    }

    private String getDescription(CSVParser csvRecords) {
        List<String> headerNames = csvRecords.getHeaderNames();
        return headerNames.size() != 0 ? headerNames.get(0) : EMPTY;
    }

    private Question createQuestion(CSVRecord csvRecord) {
        String questionValue = csvRecord.get(0);

        List<String> answers = IntStream.range(1, csvRecord.size())
                .boxed()
                .map(csvRecord::get)
                .collect(Collectors.toList());
        if (answers.size() == 0) {
            throw new RuntimeException("Questions list is empty");
        }
        return new Question(questionValue, answers);
    }
}
