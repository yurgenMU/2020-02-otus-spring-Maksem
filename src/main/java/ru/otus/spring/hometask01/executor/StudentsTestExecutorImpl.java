package ru.otus.spring.hometask01.executor;

import ru.otus.spring.hometask01.domain.Question;
import ru.otus.spring.hometask01.domain.TestData;
import ru.otus.spring.hometask01.loader.TestDataLoader;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class StudentsTestExecutorImpl implements StudentsTestExecutor {
    private static final String NAME_QUERY = "Enter your name";
    private static final String SURNAME_QUERY = "Enter your surname";
    private static final String DELIMITER = "**********".repeat(5);
    private static final String NUMERIC_REGEXP = "-?\\d+(\\.\\d+)?";

    private static final double THRESHOLD = 0.6;

    private final TestDataLoader testDataLoader;
    private Scanner scanner;

    public StudentsTestExecutorImpl(TestDataLoader testDataLoader) {
        this.testDataLoader = testDataLoader;
    }

    @Override
    public void execute() {
        scanner = new Scanner(System.in);
        doExecute();
        scanner.close();
    }

    private void doExecute() {
        System.out.println(NAME_QUERY);
        String name = scanner.nextLine().trim();
        System.out.println(SURNAME_QUERY);
        String surname = scanner.nextLine().trim();
        TestData testData = testDataLoader.getTestData();
        String description = testData.getDescription();
        List<Question> questions = testData.getQuestions();
        System.out.println(description);
        System.out.println(DELIMITER);
        boolean working = true;
        while (working) {
            Collections.shuffle(questions);
            double correctAnswersCounter = 0;
            for (Question question : questions) {
                correctAnswersCounter = processQuestion(question, correctAnswersCounter);
            }
            double accuracy = correctAnswersCounter / questions.size();
            working = processResult(name, surname, accuracy);
        }
    }

    private double processQuestion(Question question, double correctAnswersCounter) {
        System.out.println(question.getQuestionValue());
        List<String> answers = question.getAnswers();
        IntStream.range(0, answers.size())
                .forEach(i -> System.out.printf("%s) %s\n", i + 1, answers.get(i)));
        String clientAnswer = scanner.nextLine().trim();
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
        System.out.println(DELIMITER);
        if (accuracy >= THRESHOLD) {
            System.out.printf("Congratulations, %s %s. Your percentage of correct answers is %s",
                    name, surname, accuracy * 100);
            return false;
        } else {
            System.out.printf("Sorry, %s %s but the test is not passed. Your percentage of correct answers is %s." +
                    " Would you like to try again? (y/n)\n", name, surname, accuracy * 100);
            String nextTry = scanner.nextLine();
            return nextTry.equalsIgnoreCase("Y");
        }
    }

    private boolean isNumeric(String str) {
        return str.matches(NUMERIC_REGEXP);
    }
}
