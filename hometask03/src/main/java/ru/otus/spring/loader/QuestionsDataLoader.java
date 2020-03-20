package ru.otus.spring.loader;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.Props;
import ru.otus.spring.util.StudentsTestUtils;

import java.io.InputStream;
import java.util.Locale;

@Service
public class QuestionsDataLoader implements DataLoader {

    private final Props props;

    public QuestionsDataLoader(Props props) {
        this.props = props;
    }

    @Override
    public InputStream loadData() {
        String questionsFileTemplate = props.getQuestionsFileTemplate();
        Locale chosenLocale = props.getChosenLocale();
        String questionsResource =
                String.format(questionsFileTemplate, chosenLocale);

        return StudentsTestUtils.loadResource(questionsResource);
    }
}
