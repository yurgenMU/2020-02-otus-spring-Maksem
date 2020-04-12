package ru.otus.spring.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.otus.spring.util.TestUtils.*;

@DisplayName("Hibernate-based repository for genres processing")
@DataJpaTest
public class GenreRepositoryTest {
    private static final String FANTASY_GENRE = "Fantasy";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreRepository genreDao;

    @Test
    void successful_retrieval_by_id_test() {
        Genre genre = genreDao.findById(ID_1).orElseThrow();
        assertEquals(HISTORICAL_BOOKS, genre.getName());
    }

    @Test
    void retrieval_with_non_existing_id_test() {
        Genre genre = genreDao.findById(10L).orElseThrow();
        assertNull(genre);
    }

    @Test
    void successful_addition_test() {
        Genre genre = new Genre(null, RUSSIAN_CLASSIC);
        genreDao.save(genre);
        Genre actualGenre = em.find(Genre.class, ID_3);
        assertEquals(RUSSIAN_CLASSIC, actualGenre.getName());
    }

    @Test
    void successful_removal_by_id_test() {
        genreDao.deleteById(ID_1);
        assertEquals(4, getAll().size());
    }


    @Test
    void successful_removal_by_name_test() {
        genreDao.deleteByName(HISTORICAL_BOOKS);
        assertEquals(4, getAll().size());
    }

    @Test
    void successful_update_test() {
        Genre genre = new Genre(ID_3, FANTASY_GENRE);
        genreDao.save(genre);
        assertEquals(ID_3, (long) genreDao.findByName(FANTASY_GENRE).getId());
    }

    private List<Genre> getAll() {
        return (List<Genre>) genreDao.findAll();
    }
}
