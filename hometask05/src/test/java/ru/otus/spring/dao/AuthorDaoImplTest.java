package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("JDBC-based repository for genres processing")
@JdbcTest
@Import(AuthorDaoImpl.class)
public class AuthorDaoImplTest {

    @Autowired
    private AuthorDaoImpl authorDao;

    private static final String PUSHKIN = "Alexander Pushkin";

    @Test
    void successful_retrieval_by_id_test() {
        Author author = authorDao.getById(1);
        assertEquals("Nikolai Karamzin", author.getName());
    }

    @Test
    void retrieval_with_non_existing_id_test() {
        assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getById(3));
    }

    @Test
    void successful_addition_test() {
        Author author = new Author(null, PUSHKIN);
        authorDao.insert(author);
        Author actualAuthor = authorDao.getById(3);
        assertEquals(PUSHKIN, actualAuthor.getName());
    }

    @Test
    void successful_removal_by_id_test() {
        authorDao.deleteById(1);
        assertEquals(1, authorDao.getAll().size());
    }

    @Test
    void successful_removal_by_name_test() {
        authorDao.deleteByName("Valentin Pikul");
        assertEquals(1, authorDao.getAll().size());
    }

    @Test
    void successful_update_test() {
        Author author = new Author(1L, PUSHKIN);
        authorDao.update(author);
        assertEquals(1, (long) authorDao.getByName(PUSHKIN).getId());
    }

}
