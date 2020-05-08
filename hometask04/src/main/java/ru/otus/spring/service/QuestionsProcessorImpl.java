package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.Props;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestData;
import ru.otus.spring.domain.User;
import ru.otus.spring.io.IOService;
import ru.otus.spring.util.StudentsTestException;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static ru.otus.spring.util.StudentsTestUtils.isNumeric;


@Service
public class QuestionsProcessorImpl implements QuestionsProcessor {
    private static final String DELIMITER = "**********".repeat(5);

    private final IOService ioService;
    private final Props props;

    public QuestionsProcessorImpl(IOService ioService, Props props) {
        this.ioService = ioService;
        this.props = props;
    }

    @Override
    public void processQuestions(TestData testData) {
        User user = props.getUser();
        String description = testData.getDescription();
        List<Question> questions = testData.getQuestions();
        ioService.write(description);
        ioService.write(DELIMITER);
        int size = questions.size();
        if (size == 0) {
            throw new StudentsTestException("Questions are absent");
        }

        Collections.shuffle(questions);
        double correctAnswersCounter = 0;
        for (Question question : questions) {
            correctAnswersCounter = processQuestion(question, correctAnswersCounter);
        }
        double accuracy = correctAnswersCounter / size;
        processResult(user.getFirstName(), user.getLastName(), accuracy);

    }

    private double processQuestion(Question question, double correctAnswersCounter) {
        ioService.write(question.getQuestionValue());
        List<String> answers = question.getAnswers();
        Collections.shuffle(answers);
        IntStream.range(0, answers.size())
                .forEach(i -> ioService.writeFormatted("%s) %s\n", i + 1, answers.get(i)));
        String clientAnswer = ioService.read().trim();
        if (isNumeric(clientAnswer)) {
            int possibleIndex = Integer.parseInt(clientAnswer);
            if (possibleIndex <= answers.size()
                    && answers.get(possibleIndex - 1).equals(question.getCorrectAnswer())) {
                correctAnswersCounter++;
            }
        }
        return correctAnswersCounter;
    }

    private void processResult(String name, String surname, double accuracy) {
        ioService.write(DELIMITER);
        double threshold;
        String thresholdValue = props.getQuestionsFileThreshold();
        if (isNumeric(thresholdValue)) {
            threshold = Double.parseDouble(thresholdValue);
        } else {
            throw new StudentsTestException("Invalid format of threshold value!");
        }
        if (accuracy >= threshold) {
            ioService.writeFormatted(props.getSuccessMessage(),
                    name, surname, accuracy * 100, System.lineSeparator());
        } else {
            ioService.writeFormatted(props.getFailureMessage(),
                    name, surname, accuracy * 100, System.lineSeparator());
        }
    }
}