package ru.otus.spring.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Commentary;
import ru.otus.spring.domain.Genre;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.otus.spring.util.TestUtils.*;

@DisplayName("Hibernate-based repository for commentaries processing")
@DataJpaTest
@Import(CommentaryDaoJpa.class)
public class CommentaryDaoTest {

    public static final String I_LIKE_THIS_BOOK_VERY_MUCH = "I like this book very much";
    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentaryDaoJpa commentaryDao;

    @Test
    void successful_retrieval_by_id_test() {
        Commentary commentary = commentaryDao.getById(ID_1);
        assertEquals("Nice Book!", commentary.getContent());
    }

    @Test
    void retrieval_with_non_existing_id_test() {
        Commentary commentary = commentaryDao.getById(3);
        assertNull(commentary);
    }

    @Test
    void successful_addition_test() {
        Book book = em.find(Book.class, ID_1);
        String expectedContent = I_LIKE_THIS_BOOK_VERY_MUCH;
        Commentary commentary = new Commentary(expectedContent, book);
        commentaryDao.insert(commentary);
        Commentary actualCommentary = em.find(Commentary.class, ID_3);
        assertEquals(expectedContent, actualCommentary.getContent());
    }

    @Test
    void successful_removal_by_id_test() {
        commentaryDao.deleteCommentary(ID_1);
        Assert.assertNull(em.find(Commentary.class, ID_1));
    }


    @Test
    void successful_update_test() {
        String expectedContent = I_LIKE_THIS_BOOK_VERY_MUCH;
        Commentary commentary = em.find(Commentary.class, ID_1);
        em.detach(commentary);
        commentaryDao.updateCommentary(commentary.getId(), expectedContent);

        assertEquals(expectedContent, commentaryDao.getById(commentary.getId()).getContent());
    }
}
