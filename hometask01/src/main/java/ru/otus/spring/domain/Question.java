package ru.otus.spring.domain;

import java.util.List;

public class Question {
    private String questionValue;
    private List<String> answers;
    private String correctAnswer;

    public String getQuestionValue() {
        return questionValue;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public Question(String questionValue, List<String> answers) {
        this.questionValue = questionValue;
        this.answers = answers;
        this.correctAnswer = answers.get(0);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
