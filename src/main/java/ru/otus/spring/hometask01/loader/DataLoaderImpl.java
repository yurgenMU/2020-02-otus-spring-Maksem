package ru.otus.spring.hometask01.loader;

import java.io.InputStream;


public class DataLoaderImpl implements DataLoader {

    private final String questionsResource;

    public DataLoaderImpl(String questionsResource) {
        this.questionsResource = questionsResource;
    }

    @Override
    public InputStream loadData() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(questionsResource);
    }

}
