package ru.otus.spring.dao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Genre;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("JDBC-based repository for genres processing")
@DataJpaTest
@Import(GenreDaoImpl.class)
public class GenreDaoImplTest {
    private static final String FANTASY_GENRE = "Fantasy";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreDaoImpl genreDao;

    @Test
    void successful_retrieval_by_id_test() {
        Genre genre = genreDao.getById(0);
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
        Genre actualGenre = genreDao.getById(2);
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

//    @Test
//    void successful_books_by_genre_retrieval_test() {
//        Genre genre = new Genre(1L, null);
//        List<Book> booksByGenre = genreDao.getBooksByGenre(genre);
//        assertEquals(1, booksByGenre.size());
//        assertEquals("Requiem to PQ-17", booksByGenre.get(0).getName());
//    }
}
