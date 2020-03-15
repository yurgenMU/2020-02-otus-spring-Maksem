package ru.otus.spring.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.util.StudentsTestUtils;

import java.io.InputStream;

@Service
public class LanguagesDataLoader implements DataLoader {

    private String languagesResource;

    public LanguagesDataLoader(@Value("${languages.file}") String languagesResource) {
        this.languagesResource = languagesResource;
    }


    @Override
    public InputStream loadData() {
        return StudentsTestUtils.loadResource(languagesResource);
    }
}
