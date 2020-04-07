package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Author;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("JDBC-based repository for genres processing")
@DataJpaTest
@Import(AuthorDaoImpl.class)
public class AuthorDaoImplTest {

    @Autowired
    private TestEntityManager em;

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
        authorDao.deleteById(2);
        assertEquals(2, authorDao.getAll().size());
    }

    @Test
    void successful_removal_by_name_test() {
        authorDao.deleteByName("Valentin Pikul");
        assertEquals(1, authorDao.getAll().size());
    }

    @Test
    void successful_update_test() {
        Author author = new Author(3L, PUSHKIN);
        authorDao.update(author);
        assertEquals(3, (long) authorDao.getByName(PUSHKIN).getId());
    }

}
