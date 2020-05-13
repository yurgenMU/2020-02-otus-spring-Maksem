package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {

    void addBook(String name, String authorName, List<String> genres);

    Book getBook(String identifier);

    void updateBook(String bookIdentifier, String name, String authorIdentifier, List<String> genres);

    void removeBook(String identifier);

    List<Book> getAllBooks();
}
