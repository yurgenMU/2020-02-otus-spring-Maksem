package ru.otus.spring.hometask02.loader;

import java.io.InputStream;


public class ResourceFileDataLoader implements DataLoader {

    private String questionsResource;

    public ResourceFileDataLoader(String questionsResource) {
        this.questionsResource = questionsResource;
    }

    @Override
    public InputStream loadData() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(questionsResource);
    }

    public void setQuestionsResource(String questionsResource) {
        this.questionsResource = questionsResource;
    }
}
