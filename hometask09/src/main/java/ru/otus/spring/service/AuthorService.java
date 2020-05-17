package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface AuthorService {
    Author findAuthor(long id);

    void addAuthor(String name);

    void editAuthor(String identifier, String newName);

    List<Book> getAllBooksForAuthor(long id);

    void removeAuthor(String identifier);

    List<Author> getAllAuthors();
}
