package ru.otus.spring.domain;

import java.util.List;

public class Question {
    private final String questionValue;
    private final List<String> answers;
    private final String correctAnswer;

    public Question(String questionValue, List<String> answers) {
        this.questionValue = questionValue;
        this.answers = answers;
        this.correctAnswer = answers.get(0);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getQuestionValue() {
        return questionValue;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
