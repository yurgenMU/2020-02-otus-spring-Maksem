package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;

@Controller("/")
public class MainController {

    public String welcome() {
        return "index";
    }
}
