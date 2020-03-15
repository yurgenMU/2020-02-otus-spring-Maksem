package ru.otus.spring.loader;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.Props;
import ru.otus.spring.util.StudentsTestUtils;

import java.io.InputStream;

@Service
public class LanguagesDataLoader implements DataLoader {

    private final String languagesResource;

    public LanguagesDataLoader(Props props) {
        this.languagesResource = props.getLanguagesFile();
    }


    @Override
    public InputStream loadData() {
        return StudentsTestUtils.loadResource(languagesResource);
    }
}
