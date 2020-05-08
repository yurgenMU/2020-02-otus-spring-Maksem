package ru.otus.spring.executor;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.config.Props;
import ru.otus.spring.domain.User;
import ru.otus.spring.parser.QuestionsDataParser;
import ru.otus.spring.service.QuestionsProcessor;
import ru.otus.spring.service.UserDataService;

import static java.util.Objects.isNull;

@ShellComponent
public class ShellCommandsExecutor {

    private final QuestionsDataParser dataParser;
    private final QuestionsProcessor questionsProcessor;
    private final UserDataService userDataService;
    private final Props props;

    public ShellCommandsExecutor(QuestionsDataParser dataParser, QuestionsProcessor questionsProcessor, UserDataService userDataService, Props props) {
        this.dataParser = dataParser;
        this.questionsProcessor = questionsProcessor;
        this.userDataService = userDataService;
        this.props = props;
    }

    @ShellMethod(value = "Login command", key = {"login", "l", "authorize", "auth"})
    public String login(@ShellOption(defaultValue = "") String name,
                        @ShellOption(defaultValue = "") String surname) {
        User user;
        if (name.trim().length() == 0 || surname.trim().length() == 0) {
            user = userDataService.getUser();
        } else {
            user = new User(name, surname);
        }
        props.setUser(user);
        return String.format("Welcome %s %s", user.getFirstName(), user.getLastName());
    }

    @ShellMethod(value = "Logout command", key = {"logout"})
    public String logout() {
        props.setUser(null);
        return "You've successfully logged out";
    }

    @ShellMethod(value = "Login command", key = {"start test", "start"})
    public String executeTest(@ShellOption(defaultValue = "questions.csv") String path) {
        User user = props.getUser();
        if (isNull(user)) {
            return "You're not authorized to pass the test";
        }
        if (path.length() == 0) {
            return "Question source is not specified!";
        }
        props.setQuestionsResource(path);
        questionsProcessor.processQuestions(dataParser.parseData());
        return "Thank you";
    }
}