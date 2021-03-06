package ru.otus.spring.repository;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Commentary;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.spring.util.TestUtils.*;

@DisplayName("Spring Data JPA-based repository for commentaries processing")
@DataJpaTest
public class CommentaryRepositoryTest {

    public static final String I_LIKE_THIS_BOOK_VERY_MUCH = "I like this book very much";
    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentaryRepository commentaryDao;

    @Test
    void successful_retrieval_by_id_test() {
        Commentary commentary = commentaryDao.findById(ID_1).orElseThrow();
        assertEquals("Nice Book!", commentary.getContent());
    }

    @Test
    void retrieval_with_non_existing_id_test() {
        Optional<Commentary> commentary = commentaryDao.findById(ID_3);
        assertTrue(commentary.isEmpty());
    }

    @Test
    void successful_addition_test() {
        Book book = em.find(Book.class, ID_1);
        String expectedContent = I_LIKE_THIS_BOOK_VERY_MUCH;
        Commentary commentary = new Commentary(expectedContent, book);
        commentaryDao.save(commentary);
        Commentary actualCommentary = em.find(Commentary.class, ID_3);
        assertEquals(expectedContent, actualCommentary.getContent());
    }

    @Test
    void successful_removal_by_id_test() {
        commentaryDao.deleteById(ID_1);
        Assert.assertNull(em.find(Commentary.class, ID_1));
    }


    @Test
    void successful_update_test() {
        String expectedContent = I_LIKE_THIS_BOOK_VERY_MUCH;
        Commentary commentary = em.find(Commentary.class, ID_1);
        em.detach(commentary);
        commentaryDao.updateCommentary(commentary.getId(), expectedContent);

        assertEquals(expectedContent, commentaryDao.findById(commentary.getId())
                .orElseThrow().getContent());
    }
}
