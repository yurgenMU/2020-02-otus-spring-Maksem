package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Author;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.TestUtils.*;

@DisplayName("JDBC-based repository for authors processing")
@JdbcTest
@Import(AuthorDaoJdbc.class)
public class AuthorDaoJdbcTest {


    @Autowired
    private AuthorDaoJdbc authorDao;



    @Test
    void successful_retrieval_by_id_test() {
        Author author = authorDao.getById(ID_1);
        assertEquals(KARAMZIN, author.getName());
    }

    @Test
    void retrieval_with_non_existing_id_test() {
        assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getById(ID_7));
    }

    @Test
    void successful_addition_test() {
        Author author = new Author(null, GOGOL);
        authorDao.insert(author);
        Author actualAuthor = authorDao.getById(ID_6);
        assertEquals(GOGOL, actualAuthor.getName());
    }

    @Test
    void successful_removal_by_id_test() {
        authorDao.deleteById(ID_1);
        assertEquals(4, authorDao.getAll().size());
    }

    @Test
    void successful_removal_by_name_test() {
        authorDao.deleteByName(PIKUL);
        assertEquals(4, authorDao.getAll().size());
    }

    @Test
    void successful_update_test() {
        Author author = new Author(ID_1, GOGOL);
        authorDao.update(author);
        assertEquals(ID_1, (long) authorDao.getByName(GOGOL).getId());
    }

}
