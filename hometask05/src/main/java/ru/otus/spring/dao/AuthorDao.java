package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface AuthorDao {

    void insert(Author author);

    Author getById(int id);

    Author getByName(String name);

    void update(Author author);

    void deleteByName(String name);

    void deleteById(int id);

    List<Author> getAll();

    List<Book> getBooksByAuthor(Author author);
}
