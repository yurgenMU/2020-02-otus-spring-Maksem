package ru.otus.spring.hometask02.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import ru.otus.spring.hometask02.domain.Question;
import ru.otus.spring.hometask02.domain.TestData;
import ru.otus.spring.hometask02.loader.DataLoader;
import ru.otus.spring.hometask02.util.StudentsTestException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
public class DataParserImpl implements DataParser {

    private static final String EMPTY = "";
    private final DataLoader dataLoader;

    DataParserImpl(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public TestData getTestData() {
        CSVFormat csvFormat = CSVFormat.EXCEL.withFirstRecordAsHeader();
        return retrieveTestData(dataLoader.loadData(), csvFormat);
    }


    private TestData retrieveTestData(InputStream inputStream, CSVFormat csvFormat) {
        try (CSVParser csvRecords = CSVParser.parse(inputStream, StandardCharsets.UTF_8, csvFormat)) {
            String description = getDescription(csvRecords);
            List<Question> questions = StreamSupport.stream(csvRecords.spliterator(), Boolean.FALSE)
                    .map(csvRecord -> new QuestionsFactory(csvRecord).getQuestion())
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
            if (answers.size() == 0) {
                throw new RuntimeException("Questions list is empty");
            }
            question = new Question(questionValue, answers);
        }
    }
}
