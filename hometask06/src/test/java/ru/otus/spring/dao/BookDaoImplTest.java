package ru.otus.spring.dao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@DisplayName("Hibernate-based repository for genres processing")
@DataJpaTest
@Import(BookDaoImpl.class)
public class BookDaoImplTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookDaoImpl bookDao;

//    @Test
//    void successful_retrieval_by_id_test() {
//        Book book = bookDao.getById(1L);
//        Book expectedBook = em.find(Book.class, 1L);
//        assertEquals("Requiem to PQ-17", book.getName());
//        assertEquals("Valentin Pikul", book.getAuthor().getName());
//        List<Genre> genres = book.getGenres();
//        assertEquals(2, genres.size());
//        assertEquals("Historical books", genres.get(0).getName());
//    }

    @Test
    void successful_addition_test() {
        Author author = new Author(0L, null);
        Genre genre = new Genre(1L, null);

        String name = "New name";
        bookDao.insert(new Book(null, name, author, Collections.singletonList(genre)));
        assertEquals(3, bookDao.getAll().size());
        assertEquals(3, (long) bookDao.getByName(name).getId());
    }

    @Test
    void successful_removal_by_id_test() {
        bookDao.deleteById(1);
        assertEquals(1, bookDao.getAll().size());
    }

    @Test
    void successful_removal_by_name_test() {
        System.out.println(bookDao.getAll());
        bookDao.deleteByName("Requiem to PQ-17");
        System.out.println(bookDao.getAll());
        assertEquals(1, bookDao.getAll().size());
    }

    @Test
    void successful_update_test() {

        Author author = new Author(1L, null);
        Genre genre = new Genre(2L, null);

        String newName = "New name";
        bookDao.update(new Book(1L, newName, author, Collections.singletonList(genre)));
        assertEquals(1L, (long) bookDao.getByName(newName).getId());
    }

    @Test
    void successful_update_test2() {
        Book book1 = bookDao.getById(1L);

        Book toUpdate = new Book(1L, "newName", book1.getAuthor(), book1.getGenres());
        toUpdate.getGenres().add(new Genre(1L, "Military books"));
        bookDao.update(toUpdate);

        Book updated = bookDao.getById(1);
        assertThat(updated).usingRecursiveComparison().isEqualTo(toUpdate);
    }

    @Test
    void successful_update_test3() {
        Book book0 = bookDao.getById(0);
        Book book1 = bookDao.getById(1);

        bookDao.update(new Book(1L, "newName", book1.getAuthor(), book1.getGenres()));

        Book book0AfterUpdate = bookDao.getById(0);
        assertThat(book0AfterUpdate).usingRecursiveComparison().isEqualTo(book0);
    }

}
