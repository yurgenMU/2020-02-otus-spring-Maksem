package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre findGenre(long id);

    void addGenre(String name);

    void editGenre(long id, String newName);

    List<Book> getAllBooksForGenre(long id);

    void removeGenre(long id);

    List<Genre> getAllGenres();
}
