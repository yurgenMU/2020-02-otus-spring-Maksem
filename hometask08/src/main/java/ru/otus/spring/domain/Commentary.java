package ru.otus.spring.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Commentary {

    @Id
    private Long id;

    private String content;

    private Date date;

    private Book book;

    public Commentary(String content, Book book) {
        this.content = content;
        this.book = book;
        this.date = new Date();
    }

    public Commentary(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Commentary() {

    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return String.format("%s: %s \"%s\"", id, date, content);
    }
}
