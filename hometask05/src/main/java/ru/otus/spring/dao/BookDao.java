package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface BookDao {

    void insert(Book book);

    Book getById(long id);

    Book getByName(String name);

    List<Book> getBooksByGenre(Genre genre);

    List<Book> getBooksByAuthor(Author author);

    void update(Book book);

    void deleteByName(String name);

    void deleteById(long id);

    List<Book> getAll();

}
