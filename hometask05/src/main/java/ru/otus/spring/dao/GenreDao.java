package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreDao {

    void insert(Genre genre);

    Genre getById(int id);

    Genre getByName(String name);

    List<Book> getBooksByGenre(Genre genre);

    void update(Genre genre);

    void deleteByName(String name);

    void deleteById(int id);

    List<Genre> getAll();
}
