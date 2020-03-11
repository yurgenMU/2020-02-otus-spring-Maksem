package ru.otus.spring.executor;


import ru.otus.spring.domain.TestData;
import ru.otus.spring.parser.DataParser;
import ru.otus.spring.service.QuestionsProcessor;
import ru.otus.spring.service.UserDataService;

public class StudentsTestExecutorImpl implements StudentsTestExecutor {

    private final DataParser dataParser;
    private final QuestionsProcessor questionsProcessor;
    private final UserDataService userDataService;

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
