package ru.otus.spring.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import ru.otus.spring.loader.DataLoader;
import ru.otus.spring.util.StudentsTestException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@ConditionalOnMissingBean
public class LanguagesDataParser implements DataParser {

    private final DataLoader dataLoader;

    LanguagesDataParser(@Qualifier("languagesDataLoader") DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public Map<String, Locale> parseData() {
        CSVFormat csvFormat = CSVFormat.EXCEL.withSkipHeaderRecord();
        InputStream binaryData = dataLoader.loadData();
        try (CSVParser csvRecords = CSVParser.parse(binaryData, StandardCharsets.UTF_8, csvFormat)) {
            return StreamSupport.stream(csvRecords.spliterator(), Boolean.FALSE)
                    .collect(Collectors.toMap(csvRecord -> csvRecord.get(0), csvRecord -> Locale.forLanguageTag(csvRecord.get(1))));
        } catch (Exception e) {
            throw new StudentsTestException("Error while parsing data", e);
        }
    }
}
