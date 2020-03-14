
package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.Props;
import ru.otus.spring.io.IOService;
import ru.otus.spring.parser.LanguagesDataParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;

import static ru.otus.spring.util.StudentsTestUtils.isNumeric;


@Service
public class SettingsServiceImpl implements SettingsService {

    private final IOService ioService;
    private final LanguagesDataParser languagesDataParser;
    private Locale chosenLocale;

    private final String questionsPathTemplate;

    private final String defaultLocaleLanguage;

    private final  String defaultLocaleCountry;

    public SettingsServiceImpl(IOService ioService, LanguagesDataParser languagesDataParser, Props props) {
        this.ioService = ioService;
        this.languagesDataParser = languagesDataParser;
        this.questionsPathTemplate = props.getQuestionsFileTemplate();
        this.defaultLocaleLanguage = props.getDefaultLocaleLanguage();
        this.defaultLocaleCountry = props.getDefaultLocaleCountry();
    }

    @Override
    public void chooseLocale() {

        Map<String, Locale> locales = languagesDataParser.parseData();
        List<String> languages = new ArrayList<>(locales.keySet());
        IntStream.range(0, languages.size())
                .forEach(i -> ioService.writeFormatted("%s) %s\n", i + 1, languages.get(i)));
        String chosenCandidate = ioService.read();
        if (isNumeric(chosenCandidate)) {
            int chosenNumber = Integer.parseInt(chosenCandidate);
            if (chosenNumber >= 1 && chosenNumber <= languages.size()) {
                this.chosenLocale = locales.get(languages.get(chosenNumber - 1));
                return;
            }
        }
        this.chosenLocale = new Locale(defaultLocaleLanguage, defaultLocaleCountry);
    }

    @Override
    public Locale getChosenLocale() {
        return chosenLocale;
    }

    @Override
    public String getQuestionsResource() {
        return String.format(questionsPathTemplate, chosenLocale);
    }
}
