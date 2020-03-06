package ru.otus.spring.hometask02.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.hometask02.domain.TestData;
import ru.otus.spring.hometask02.parser.DataParser;
import ru.otus.spring.hometask02.service.QuestionsProcessor;
import ru.otus.spring.hometask02.service.UserDataService;

@Service
public class StudentsTestExecutorImpl implements StudentsTestExecutor {

    private final DataParser dataParser;
    private final QuestionsProcessor questionsProcessor;
    private final UserDataService userDataService;

    @Autowired
    public StudentsTestExecutorImpl(DataParser dataParser, UserDataService userDataService,
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
        String name = userDataService.getFirstName();
        String surname = userDataService.getLastName();
        TestData testData = dataParser.getTestData();
        questionsProcessor.processQuestions(name, surname, testData);
    }

}
