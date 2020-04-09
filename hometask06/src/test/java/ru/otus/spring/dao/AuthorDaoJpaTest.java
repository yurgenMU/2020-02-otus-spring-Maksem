package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.otus.spring.util.TestUtils.*;

@DisplayName("Hibernate-based repository for authors processing")
@DataJpaTest
@Import(AuthorDaoJpa.class)
public class AuthorDaoJpaTest {

    private static final String PUSHKIN = "Alexander Pushkin";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorDao authorDao;

    @Test
    void successful_retrieval_by_id_test() {
        Author author = authorDao.getById(ID_1);
        assertEquals(KARAMZIN, author.getName());
    }

    @Test
    void retrieval_with_non_existing_id_test() {
        Author author = authorDao.getById(10);
        assertNull(author);
    }

    @Test
    void successful_addition_test() {
        Author author = new Author(null, GOGOL);
        authorDao.insert(author);
        Author actualAuthor = em.find(Author.class, ID_6);
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
