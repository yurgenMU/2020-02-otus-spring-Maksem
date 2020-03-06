package ru.otus.spring.hometask02.service;

import ru.otus.spring.hometask02.domain.TestData;

public interface QuestionsProcessor {

    void processQuestions(String name, String surname, TestData testData);
}
