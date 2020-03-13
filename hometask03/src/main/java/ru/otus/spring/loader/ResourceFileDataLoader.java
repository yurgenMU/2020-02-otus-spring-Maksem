package ru.otus.spring.loader;

import ru.otus.spring.service.SettingsService;

import java.io.InputStream;
import java.util.Objects;


public class ResourceFileDataLoader implements DataLoader {

    private final String questionsResource;
    private final SettingsService settingsService;

    public ResourceFileDataLoader(String questionsResource) {
        this.questionsResource = questionsResource;
        this.settingsService = null;
    }

    public ResourceFileDataLoader(SettingsService settingsService) {
        this.questionsResource = null;
        this.settingsService = settingsService;
    }

    @Override
    public InputStream loadData() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        if (questionsResource == null) {
            return classloader.getResourceAsStream(Objects.requireNonNull(settingsService).getQuestionsResource());
        }
        return classloader.getResourceAsStream(questionsResource);
    }
}
