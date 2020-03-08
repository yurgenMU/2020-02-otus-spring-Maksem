package ru.otus.spring.hometask02.service;

import org.springframework.context.MessageSource;
import ru.otus.spring.hometask02.domain.Question;
import ru.otus.spring.hometask02.domain.TestData;
import ru.otus.spring.hometask02.io.IOService;
import ru.otus.spring.hometask02.util.StudentsTestException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import static ru.otus.spring.hometask02.util.StudentsTestUtils.isNumeric;


public class QuestionsProcessorImpl implements QuestionsProcessor {
    private static final String DELIMITER = "**********".repeat(5);
    private static final String THRESHOLD_PROPERTY_NAME = "questions.threshold";
    private static final String SUCCESS_PROPERTY_NAME = "questions.success";
    private static final String FAILURE_PROPERTY_NAME = "questions.failure";
    private static final String CONFIRM_PROPERTY_NAME = "questions.confirm";


    private final IOService ioService;
    private final MessageSource messageSource;
    private final LanguagesService languagesService;

    public QuestionsProcessorImpl(IOService ioService, MessageSource messageSource, LanguagesService languagesService) {
        this.ioService = ioService;
        this.messageSource = messageSource;
        this.languagesService = languagesService;
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
        String thresholdValue = messageSource.getMessage(THRESHOLD_PROPERTY_NAME, null
                , languagesService.getChosenLocale());
        double threshold;
        if (isNumeric(thresholdValue)) {
            threshold = Double.parseDouble(thresholdValue);
        } else {
            threshold = Double.parseDouble(messageSource.getMessage(THRESHOLD_PROPERTY_NAME,
                    null, Locale.ENGLISH));
        }
        if (accuracy >= threshold) {
            ioService.writeFormatted(messageSource.getMessage(SUCCESS_PROPERTY_NAME, null,
                    languagesService.getChosenLocale()),
                    name, surname, accuracy * 100, System.lineSeparator());
            return false;
        } else {
            ioService.writeFormatted(messageSource.getMessage(FAILURE_PROPERTY_NAME, null,
                    languagesService.getChosenLocale()),
                    name, surname, accuracy * 100, System.lineSeparator());
            String nextTry = ioService.read();
            return nextTry.equalsIgnoreCase(messageSource.getMessage(CONFIRM_PROPERTY_NAME,
                    null, languagesService.getChosenLocale()));
        }
    }
}
