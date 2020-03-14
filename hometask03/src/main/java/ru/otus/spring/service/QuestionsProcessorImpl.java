package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.Props;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestData;
import ru.otus.spring.io.IOService;
import ru.otus.spring.util.StudentsTestException;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static ru.otus.spring.util.StudentsTestUtils.isNumeric;


@Service
public class QuestionsProcessorImpl implements QuestionsProcessor {
    private static final String DELIMITER = "**********".repeat(5);
    private static final String SUCCESS_PROPERTY_NAME = "questions.success";
    private static final String FAILURE_PROPERTY_NAME = "questions.failure";
    private static final String CONFIRM_PROPERTY_NAME = "questions.confirm";

    private final String thresholdValue;

    private final IOService ioService;
    private final LocalizationService localizationService;

    public QuestionsProcessorImpl(IOService ioService, LocalizationService localizationService, Props props) {
        this.ioService = ioService;
        this.localizationService = localizationService;
        this.thresholdValue = props.getQuestionsFileThreshold();
    }

    @Override
    public void processQuestions(String name, String surname, TestData testData) {
        String description = testData.getDescription();
        List<Question> questions = testData.getQuestions();
        ioService.write(description);
        ioService.write(DELIMITER);
        boolean working = true;
        int size = questions.size();
        if (size == 0) {
            throw new StudentsTestException("Questions are absent");
        }
        while (working) {
            Collections.shuffle(questions);
            double correctAnswersCounter = 0;
            for (Question question : questions) {
                correctAnswersCounter = processQuestion(question, correctAnswersCounter);
            }
            double accuracy = correctAnswersCounter / size;
            working = processResult(name, surname, accuracy);
        }
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

    private boolean processResult(String name, String surname, double accuracy) {
        ioService.write(DELIMITER);
        double threshold;
        if (isNumeric(thresholdValue)) {
            threshold = Double.parseDouble(thresholdValue);
        } else {
            throw new StudentsTestException("Invalid format of threshold value!");
        }
        if (accuracy >= threshold) {
            ioService.writeFormatted(localizationService.getLocalizedMessage(SUCCESS_PROPERTY_NAME),
                    name, surname, accuracy * 100, System.lineSeparator());
            return false;
        } else {
            ioService.writeFormatted(localizationService.getLocalizedMessage(FAILURE_PROPERTY_NAME),
                    name, surname, accuracy * 100, System.lineSeparator());
            String nextTry = ioService.read();
            return nextTry.equalsIgnoreCase(localizationService.getLocalizedMessage(CONFIRM_PROPERTY_NAME));
        }
    }
}