package ru.otus.spring.executor;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.TestData;
import ru.otus.spring.domain.User;
import ru.otus.spring.parser.QuestionsDataParser;
import ru.otus.spring.service.SettingsService;
import ru.otus.spring.service.QuestionsProcessor;
import ru.otus.spring.service.UserDataService;

@Service
public class StudentsTestExecutorImpl implements StudentsTestExecutor {

    private final QuestionsDataParser dataParser;
    private final QuestionsProcessor questionsProcessor;
    private final UserDataService userDataService;
    private final SettingsService settingsService;

    public StudentsTestExecutorImpl(QuestionsDataParser dataParser, UserDataService userDataService,
                                    QuestionsProcessor questionsProcessor, SettingsService settingsService) {
        this.dataParser = dataParser;
        this.questionsProcessor = questionsProcessor;
        this.userDataService = userDataService;
        this.settingsService = settingsService;
    }

    @Override
    public void execute() {
        doExecute();
    }

    private void doExecute() {
        settingsService.chooseLocale();
        User user = userDataService.getUser();
        String name = user.getFirstName();
        String surname = user.getLastName();
        TestData testData = dataParser.parseData();

        questionsProcessor.processQuestions(name, surname, testData);
    }

}
