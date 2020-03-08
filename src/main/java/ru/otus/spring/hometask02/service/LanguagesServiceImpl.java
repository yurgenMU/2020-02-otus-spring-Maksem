
package ru.otus.spring.hometask02.service;

import ru.otus.spring.hometask02.io.IOService;
import ru.otus.spring.hometask02.parser.LanguagesDataParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;

import static ru.otus.spring.hometask02.util.StudentsTestUtils.isNumeric;

public class LanguagesServiceImpl implements LanguagesService {

    private final IOService ioService;
    private final LanguagesDataParser languagesDataParser;
    private Locale chosenLocale;

    public LanguagesServiceImpl(IOService ioService, LanguagesDataParser languagesDataParser) {
        this.ioService = ioService;
        this.languagesDataParser = languagesDataParser;
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
        this.chosenLocale = Locale.getDefault();
    }

    @Override
    public Locale getChosenLocale() {
        return chosenLocale;
    }
}
