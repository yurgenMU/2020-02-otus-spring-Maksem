package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreService {

    void addGenre(String name);

    void editGenre(String identifier, String newName);

    List<Book> getAllBooksForGenre(String identifier);

    void removeGenre(String identifier);

    List<Genre> getAllGenres();
}
