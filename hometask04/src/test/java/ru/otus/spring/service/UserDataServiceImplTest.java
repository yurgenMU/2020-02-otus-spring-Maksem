package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.config.Props;
import ru.otus.spring.domain.User;
import ru.otus.spring.io.IOService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UserDataServiceImplTest {

    @MockBean
    IOService ioService;

    @MockBean
    Props props;

    @Autowired
    UserDataServiceImpl userDataService;

    @BeforeEach
    void init() {
        doNothing().when(ioService).writeFormatted(anyString());
    }

    @Test
    void simpleTest() {
        when(ioService.read()).thenReturn("Ivan").thenReturn("Ivanov");
        assertEquals(new User("Ivan", "Ivanov"), userDataService.getUser());
    }

}
