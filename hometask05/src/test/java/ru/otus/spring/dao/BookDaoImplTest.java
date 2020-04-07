package ru.otus.spring.dao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@DisplayName("JDBC-based repository for genres processing")
@JdbcTest
@Import(BookDaoImpl.class)
public class BookDaoImplTest {

    @Autowired
    private BookDaoImpl bookDao;

    @Test
    void successful_retrieval_by_id_test() {
        Book book = bookDao.getById(1);
        assertEquals("Requiem to PQ-17", book.getName());
        assertEquals("Valentin Pikul", book.getAuthor().getName());
        List<Genre> genres = book.getGenres();
        assertEquals(2, genres.size());
        assertEquals("Historical books", genres.get(0).getName());
    }

    @Test
    void successful_addition_test() {
        Author author = new Author(1L, null);
        Genre genre = new Genre(2L, null);

        String name = "New name";
        bookDao.insert(new Book(null, name, author, Collections.singletonList(genre)));
        assertEquals(3, bookDao.getAll().size());
        assertEquals(3, (long) bookDao.getByName(name).getId());
    }

    @Test
    void successful_removal_by_id_test() {
        bookDao.deleteById(1);
        assertEquals(1, bookDao.getAll().size());
    }

    @Test
    void successful_removal_by_name_test() {
        System.out.println(bookDao.getAll());
        bookDao.deleteByName("Requiem to PQ-17");
        System.out.println(bookDao.getAll());
        assertEquals(1, bookDao.getAll().size());
    }

    @Test
    void successful_update_test() {

        Author author = new Author(1L, null);
        Genre genre = new Genre(2L, null);

        String newName = "New name";
        bookDao.update(new Book(1L, newName, author, Collections.singletonList(genre)));
        assertEquals(1, (long) bookDao.getByName(newName).getId());
    }

    @Test
    void successful_update_test2() {
        Book book1 = bookDao.getById(2);

        Book toUpdate = new Book(2L, "newName", book1.getAuthor(), book1.getGenres());
        toUpdate.getGenres().add(new Genre(2L, "Military books"));
        bookDao.update(toUpdate);

        Book updated = bookDao.getById(2);
        assertThat(updated).usingRecursiveComparison().isEqualTo(toUpdate);
    }

    @Test
    void successful_update_test3() {
        Book book1 = bookDao.getById(1);
        Book book2 = bookDao.getById(2);

        bookDao.update(new Book(2L, "newName", book2.getAuthor(), book2.getGenres()));

        Book book0AfterUpdate = bookDao.getById(1);
        assertThat(book0AfterUpdate).usingRecursiveComparison().isEqualTo(book1);
    }


    @Test
    void successful_books_by_genre_retrieval_test() {
        Genre genre = new Genre(2L, null);
        List<Book> booksByGenre = bookDao.getBooksByGenre(genre);
        System.out.println(bookDao.getAll());
        assertEquals(1, booksByGenre.size());
        assertEquals("Requiem to PQ-17", booksByGenre.get(0).getName());
    }

    @Test
    void get_books_by_author_test() {
        Author author = new Author(null, "Valentin Pikul");
        List<Book> booksByAuthor = bookDao.getBooksByAuthor(author);
        assertEquals(1, booksByAuthor.size());
        assertEquals("Requiem to PQ-17", booksByAuthor.get(0).getName());
    }



}
