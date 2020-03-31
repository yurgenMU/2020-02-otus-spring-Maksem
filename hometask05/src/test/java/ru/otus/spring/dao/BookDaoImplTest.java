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

import static org.junit.Assert.assertEquals;

@DisplayName("JDBC-based repository for genres processing")
@JdbcTest
@Import(BookDaoImpl.class)
public class BookDaoImplTest {

    @Autowired
    private BookDaoImpl bookDao;

    @Test
    void successful_retrieval_by_id_test() {
        Book book = bookDao.getById(0);
        assertEquals("Requiem to PQ-17", book.getName());
        assertEquals("Valentin Pikul", book.getAuthor().getName());
        List<Genre> genres = book.getGenres();
        assertEquals(2, genres.size());
        assertEquals("Historical books", genres.get(0).getName());
    }

    @Test
    void successful_addition_test() {
        Author author = new Author(0, null);
        Genre genre = new Genre(1, null);

        String name = "New name";
        bookDao.insert(new Book(null, name, author, Collections.singletonList(genre)));
        assertEquals(3, bookDao.getAll().size());
        assertEquals(2, (int) bookDao.getByName(name).getId());
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

        Author author = new Author(0, null);
        Genre genre = new Genre(1, null);

        String newName = "New name";
        bookDao.update(new Book(0, newName, author, Collections.singletonList(genre)));
        assertEquals(0, (int) bookDao.getByName(newName).getId());
    }

}
