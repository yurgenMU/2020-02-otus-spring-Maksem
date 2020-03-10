package ru.otus.spring.hometask02.executor;

import org.springframework.stereotype.Service;
import ru.otus.spring.hometask02.domain.TestData;
import ru.otus.spring.hometask02.domain.User;
import ru.otus.spring.hometask02.parser.QuestionsDataParser;
import ru.otus.spring.hometask02.service.QuestionsProcessor;
import ru.otus.spring.hometask02.service.UserDataService;

@Service
public class StudentsTestExecutorImpl implements StudentsTestExecutor {

    private final QuestionsDataParser dataParser;
    private final QuestionsProcessor questionsProcessor;
    private final UserDataService userDataService;

    public StudentsTestExecutorImpl(QuestionsDataParser dataParser, UserDataService userDataService,
                                    QuestionsProcessor questionsProcessor) {
        this.dataParser = dataParser;
        this.questionsProcessor = questionsProcessor;
        this.userDataService = userDataService;
    }

    @Override
    public void execute() {
        doExecute();
    }

    private void doExecute() {
        User user = userDataService.getUser();
        String name = user.getFirstName();
        String surname = user.getLastName();
        TestData testData = dataParser.parseData();

        questionsProcessor.processQuestions(name, surname, testData);
    }

}
