package ru.otus.spring.hometask01.domain;

import java.util.List;

public class TestData {
    private final String description;
    private final List<Question> questions;

    public TestData(String description, List<Question> questions) {
        this.description = description;
        this.questions = questions;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
