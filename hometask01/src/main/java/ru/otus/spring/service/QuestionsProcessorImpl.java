package ru.otus.spring.service;


import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestData;
import ru.otus.spring.io.IOService;
import ru.otus.spring.util.StudentsTestException;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;


public class QuestionsProcessorImpl implements QuestionsProcessor {
    private static final String DELIMITER = "**********".repeat(5);
    private static final String NUMERIC_REGEXP = "-?\\d+(\\.\\d+)?";

    private static final double THRESHOLD = 0.6;

    private final IOService ioService;

    public QuestionsProcessorImpl(IOService ioService) {
        this.ioService = ioService;
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
        if (accuracy >= THRESHOLD) {
            ioService.writeFormatted("Congratulations, %s %s. Your percentage of correct answers is %s",
                    name, surname, accuracy * 100);
            return false;
        } else {
            ioService.writeFormatted("Sorry, %s %s but the test is not passed. Your percentage of correct answers is %s." +
                    " Would you like to try again? (y/n)\n", name, surname, accuracy * 100);
            String nextTry = ioService.read();
            return nextTry.equalsIgnoreCase("Y");
        }
    }

    private boolean isNumeric(String str) {
        return str.matches(NUMERIC_REGEXP);
    }

}
