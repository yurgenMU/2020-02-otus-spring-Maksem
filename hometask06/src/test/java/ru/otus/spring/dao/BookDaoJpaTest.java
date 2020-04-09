package ru.otus.spring.dao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static ru.otus.spring.util.TestUtils.*;

@DisplayName("Hibernate-based repository for books processing")
@DataJpaTest
@Import(BookDaoJpa.class)
public class BookDaoJpaTest {


    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookDaoJpa bookDao;

    @Test
    void successful_retrieval_by_id_test() {
        Book book = bookDao.getById(ID_1);
        assertEquals(PQ_17, book.getName());
        assertEquals(PIKUL, book.getAuthor().getName());
        List<Genre> genres = book.getGenres();
        assertEquals(2, genres.size());
        assertEquals(MILITARY_BOOKS, genres.get(1).getName());
    }

    @Test
    void successful_addition_test() {
        Author author = new Author(ID_1, null);
        Genre genre = new Genre(ID_2, null);

        Book bookToAdd = new Book(null, NEW_NAME, author, Collections.singletonList(genre));
        bookDao.insert(bookToAdd);
        assertEquals(7, bookDao.getAll().size());
        assertEquals(ID_7, (long) bookDao.getByName(NEW_NAME).getId());
    }

    @Test
    void successful_removal_by_id_test() {
        bookDao.deleteById(ID_1);
        assertEquals(5, bookDao.getAll().size());
    }

    @Test
    void successful_removal_by_name_test() {
        System.out.println(bookDao.getAll());
        bookDao.deleteByName(PQ_17);
        System.out.println(bookDao.getAll());
        assertEquals(5, bookDao.getAll().size());
    }

    @Test
    void successful_update_test() {

        Author author = new Author(ID_1, null);
        Genre genre = new Genre(ID_2, null);

        Book toUpdate = new Book(ID_1, NEW_NAME, author, Collections.singletonList(genre));
        bookDao.update(toUpdate);
        assertThat(em.find(Book.class, ID_1)).usingRecursiveComparison().isEqualTo(toUpdate);
    }

    @Test
    void successful_update_test2() {
        Book book1 = bookDao.getById(2);

        Book toUpdate = new Book(ID_2, NEW_NAME, book1.getAuthor(), book1.getGenres());
        toUpdate.getGenres().add(new Genre(ID_2, MILITARY_BOOKS));
        bookDao.update(toUpdate);

        Book updated = em.find(Book.class, ID_2);
        assertThat(updated).usingRecursiveComparison().isEqualTo(toUpdate);
    }

    @Test
    void successful_update_test3() {
        Book book1 = bookDao.getById(1);
        Book book2 = bookDao.getById(2);

        bookDao.update(new Book(2L, "newName", book2.getAuthor(), book2.getGenres()));

        Book book0AfterUpdate = em.find(Book.class,ID_1);
        assertThat(book0AfterUpdate).usingRecursiveComparison().isEqualTo(book1);
    }


    @Test
    void successful_books_by_genre_retrieval_test() {
        Genre genre = new Genre(4L, null);
        List<Book> booksByGenre = bookDao.getBooksByGenre(genre);
        System.out.println(bookDao.getAll());
        assertEquals(1, booksByGenre.size());
        assertEquals(CLEAN_CODE, booksByGenre.get(0).getName());
    }

    @Test
    void get_books_by_author_test() {
        Author author = em.find(Author.class, ID_2);
        List<Book> booksByAuthor = bookDao.getBooksByAuthor(author);
        assertEquals(1, booksByAuthor.size());
        assertEquals(PQ_17, booksByAuthor.get(0).getName());
    }

}
