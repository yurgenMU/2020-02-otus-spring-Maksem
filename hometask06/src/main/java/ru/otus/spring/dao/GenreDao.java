package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreDao {

    void insert(Genre genre);

    Genre getById(long id);

    Genre getByName(String name);

    void update(Genre genre);

    void deleteByName(String name);

    void deleteById(long id);

    List<Genre> getAll();
}
