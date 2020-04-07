package ru.otus.spring.dao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("JDBC-based repository for genres processing")
@JdbcTest
@Import(GenreDaoImpl.class)
public class GenreDaoImplTest {
    private static final String FANTASY_GENRE = "Fantasy";

    @Autowired
    private GenreDaoImpl genreDao;

    @Test
    void successful_retrieval_by_id_test() {
        Genre genre = genreDao.getById(1);
        assertEquals("Historical books", genre.getName());
    }

    @Test
    void retrieval_with_non_existing_id_test() {
        assertThrows(EmptyResultDataAccessException.class, () -> genreDao.getById(3));
    }

    @Test
    void successful_addition_test() {
        Genre genre = new Genre(null, FANTASY_GENRE);
        genreDao.insert(genre);
        Genre actualGenre = genreDao.getById(3);
        assertEquals(FANTASY_GENRE, actualGenre.getName());
    }

    @Test
    void successful_removal_by_id_test() {
        genreDao.deleteById(1);
        assertEquals(1, genreDao.getAll().size());
    }

    @Test
    void successful_removal_by_name_test() {
        genreDao.deleteByName("Historical books");
        assertEquals(1, genreDao.getAll().size());
    }

    @Test
    void successful_update_test() {
        Genre genre = new Genre(1L, FANTASY_GENRE);
        genreDao.update(genre);
        assertEquals(1, (long) genreDao.getByName(FANTASY_GENRE).getId());
    }

}
