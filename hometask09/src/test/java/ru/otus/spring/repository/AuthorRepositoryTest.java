package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.otus.spring.util.TestUtils.*;

@DisplayName("Spring Data JPA-based repository for authors processing")
@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void successful_retrieval_by_id_test() {
        Author author = authorRepository.findById(ID_1).orElseThrow();
        assertEquals(KARAMZIN, author.getName());
    }

    @Test
    void retrieval_with_non_existing_id_test() {
        Optional<Author> author = authorRepository.findById(10L);
        assertTrue(author.isEmpty());
    }

    @Test
    void successful_addition_test() {
        Author author = new Author(null, GOGOL);
        authorRepository.save(author);
        Author actualAuthor = em.find(Author.class, ID_6);
        assertEquals(GOGOL, actualAuthor.getName());
    }

    @Test
    void successful_removal_by_id_test() {
        authorRepository.deleteById(ID_1);
        assertEquals(4, getAll().size());
    }

    @Test
    void successful_removal_by_name_test() {
        authorRepository.deleteById(ID_2);
        assertNull(em.find(Author.class, ID_2));
    }

    @Test
    void successful_update_test() {
        Author author = new Author(ID_1, GOGOL);
        authorRepository.save(author);
        assertEquals(GOGOL, authorRepository.findById(ID_1).orElseThrow().getName());
    }

    private List<Author> getAll() {
        return (List<Author>) authorRepository.findAll();
    }
}
