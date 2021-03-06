package ru.otus.spring.dao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Genre;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.TestUtils.*;

@DisplayName("JDBC-based repository for genres processing")
@JdbcTest
@Import(GenreDaoJdbc.class)
public class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc genreDao;

    @Test
    void successful_retrieval_by_id_test() {
        Genre genre = genreDao.getById(1);
        assertEquals(HISTORICAL_BOOKS, genre.getName());
    }

    @Test
    void retrieval_with_non_existing_id_test() {
        assertThrows(EmptyResultDataAccessException.class, () -> genreDao.getById(10L));
    }

    @Test
    void successful_addition_test() {
        Genre genre = new Genre(null, RUSSIAN_CLASSIC);
        genreDao.insert(genre);
        Genre actualGenre = genreDao.getById(ID_3);
        assertEquals(RUSSIAN_CLASSIC, actualGenre.getName());
    }

    @Test
    void successful_removal_by_id_test() {
        genreDao.deleteById(ID_1);
        assertEquals(4, genreDao.getAll().size());
    }

    @Test
    void successful_removal_by_name_test() {
        genreDao.deleteByName(HISTORICAL_BOOKS);
        assertEquals(4, genreDao.getAll().size());
    }

    @Test
    void successful_update_test() {
        Genre genre = new Genre(ID_3, FANTASY_GENRE);
        genreDao.update(genre);
        assertEquals(ID_3, (long) genreDao.getByName(FANTASY_GENRE).getId());
    }

}
