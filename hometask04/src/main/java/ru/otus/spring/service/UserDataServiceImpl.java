package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.Props;
import ru.otus.spring.domain.User;
import ru.otus.spring.io.IOService;

@Service
public class UserDataServiceImpl implements UserDataService {

    private final IOService ioService;
    private final Props props;

    public UserDataServiceImpl(IOService ioService, Props props) {
        this.ioService = ioService;
        this.props = props;
    }

    @Override
    public User getUser() {
        return new User(getFirstName(), getLastName());
    }

    private String getFirstName() {
        return doReadName(props.getNameQuery());
    }

    private String getLastName() {
        return doReadName(props.getSurnameQuery());
    }

    private String doReadName(String query) {
        ioService.write(query);
        return ioService.read().trim();
    }
}
