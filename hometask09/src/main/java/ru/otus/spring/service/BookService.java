package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {

    void addBook(String name, long authorId, List<String> genres);

    Book getBook(long id);

    void updateBook(long id, String name, long authorId, List<String> genres);

    void removeBook(long id);

    List<Book> getAllBooks();
}
