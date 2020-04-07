package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Commentary;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Hibernate-based repository for commentaries processing")
@DataJpaTest
@Import(CommentaryDaoImpl.class)
public class CommentaryDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentaryDaoImpl commentaryDao;

    @Test
    void successful_retrieval_by_id_test() {
        Commentary commentary = commentaryDao.getById(1);
        assertEquals("Nice Book!", commentary.getContent());
    }

    @Test
    void retrieval_with_non_existing_id_test() {
        Commentary commentary = commentaryDao.getById(3);
        assertNull(commentary);
    }

    @Test
    void successful_addition_test() {
        Book book = em.find(Book.class, 1L);
        String expectedContent = "I like this book very much";
        Commentary commentary = new Commentary(expectedContent, book);
        commentaryDao.insert(commentary);
        Commentary actualCommentary = commentaryDao.getById(3);
        assertEquals(expectedContent, actualCommentary.getContent());
    }

    @Test
    void successful_removal_by_id_test() {
        commentaryDao.deleteCommentary(1L);
        Book book = em.find(Book.class, 1L);
        assertEquals(0, commentaryDao.getCommentariesByBook(book).size());
    }


    @Test
    void successful_update_test() {
        String expectedContent = "I like this book very much";
        Commentary commentary = em.find(Commentary.class, 1L);
        em.detach(commentary);
        commentaryDao.updateCommentary(commentary.getId(), expectedContent);

        assertEquals(expectedContent, commentaryDao.getById(commentary.getId()).getContent());
    }
}
