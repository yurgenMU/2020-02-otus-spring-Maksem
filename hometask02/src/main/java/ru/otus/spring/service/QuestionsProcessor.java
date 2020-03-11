package ru.otus.spring.service;


import ru.otus.spring.domain.TestData;

public interface QuestionsProcessor {

    void processQuestions(String name, String surname, TestData testData);
}
