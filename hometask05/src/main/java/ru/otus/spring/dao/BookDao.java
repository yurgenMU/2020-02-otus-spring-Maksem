package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {

    void insert(Book book);

    Book getById(int id);

    Book getByName(String name);

    void update(Book book);

    void deleteByName(String name);

    void deleteById(int id);

    List<Book> getAll();

}
