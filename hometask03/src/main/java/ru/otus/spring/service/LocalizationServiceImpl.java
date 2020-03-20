package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.Props;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final Props props;
    private final MessageSource messageSource;

    public LocalizationServiceImpl(Props props, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.props = props;
    }

    @Override
    public String getLocalizedMessage(String key) {
        return messageSource.getMessage(key, null, props.getChosenLocale());
    }
}
