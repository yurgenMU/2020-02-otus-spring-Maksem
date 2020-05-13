package ru.otus.spring.repositories;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.AbstractRepositoryTest;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

import static org.junit.Assert.*;
import static ru.otus.spring.util.TestUtils.ID_1;

@ComponentScan("ru.otus.spring.events")
public class GenresRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void removal_books_while_genre_removal_test() {
        List<Book> books = bookRepository.findAll();
        assertTrue(books.stream().anyMatch(book -> book.getGenres().stream().map(Genre::getId)
                .anyMatch(x -> x.equals(ID_1))));
        genreRepository.deleteById(ID_1);
        books = bookRepository.findAll();
        assertTrue(books.stream().anyMatch(book -> book.getGenres().stream().map(Genre::getId)
                .noneMatch(x -> x.equals(ID_1))));
    }

}
