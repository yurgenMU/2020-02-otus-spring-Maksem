package ru.otus.spring.dao;


import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Genre;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.otus.spring.util.TestUtils.*;

@DisplayName("Hibernate-based repository for genres processing")
@DataJpaTest
@Import(GenreDaoJpa.class)
public class GenreDaoJpaTest {
    private static final String FANTASY_GENRE = "Fantasy";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreDaoJpa genreDao;

    @Test
    void successful_retrieval_by_id_test() {
        Genre genre = genreDao.getById(1);
        assertEquals(HISTORICAL_BOOKS, genre.getName());
    }

    @Test
    void retrieval_with_non_existing_id_test() {
        Genre genre = genreDao.getById(10);
        assertNull(genre);
    }

    @Test
    void successful_addition_test() {
        Genre genre = new Genre(null, RUSSIAN_CLASSIC);
        genreDao.insert(genre);
        Genre actualGenre = em.find(Genre.class, ID_3);
        assertEquals(RUSSIAN_CLASSIC, actualGenre.getName());
    }

    @Test
    void successful_removal_by_id_test() {
        genreDao.deleteById(ID_1);
        Assert.assertNull(em.find(Genre.class, ID_1));
    }

    @Test
    void successful_removal_by_name_test() {
        genreDao.deleteByName(HISTORICAL_BOOKS);
        Assert.assertNull(em.find(Genre.class, ID_1));
    }

    @Test
    void successful_update_test() {
        Genre genre = new Genre(ID_3, FANTASY_GENRE);
        genreDao.update(genre);
        assertEquals(ID_3, (long) genreDao.getByName(FANTASY_GENRE).getId());
    }

}
