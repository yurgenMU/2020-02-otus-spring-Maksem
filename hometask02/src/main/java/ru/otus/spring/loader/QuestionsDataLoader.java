package ru.otus.spring.loader;

import org.springframework.stereotype.Service;
import ru.otus.spring.service.LanguagesService;
import ru.otus.spring.util.StudentsTestException;
import ru.otus.spring.util.StudentsTestUtils;

import java.io.InputStream;

@Service
public class QuestionsDataLoader implements DataLoader {

    private final LanguagesService languagesService;

    public QuestionsDataLoader(LanguagesService languagesService) {
        this.languagesService = languagesService;
    }

    @Override
    public InputStream loadData() {
        String questionsResource = languagesService.getQuestionsResource();
        if (questionsResource == null) {
            throw new StudentsTestException("Required resource is empty");
        }
        return StudentsTestUtils.loadResource(questionsResource);
    }
}
