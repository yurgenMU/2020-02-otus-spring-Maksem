package ru.otus.spring.hometask01.service;

import ru.otus.spring.hometask01.domain.TestData;

public interface QuestionsProcessor {

    void processQuestions(String name, String surname, TestData testData);
}
