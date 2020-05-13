package ru.otus.spring.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.AbstractRepositoryTest;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.otus.spring.util.TestUtils.ID_1;

@ComponentScan("ru.otus.spring.events")
public class AuthorsRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void removal_books_while_author_removal_test() {
        List<Book> books = bookRepository.findAll();
        assertTrue(books.stream().anyMatch(book -> book.getAuthor().getId().equals(ID_1)));
        authorRepository.deleteById(ID_1);
        books = bookRepository.findAll();
        assertTrue(books.stream().noneMatch(book -> book.getAuthor().getId().equals(ID_1)));
    }
    
}
