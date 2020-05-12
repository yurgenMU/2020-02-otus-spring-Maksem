//package ru.otus.spring.repositories;
//
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.context.annotation.ComponentScan;
//import ru.otus.spring.AbstractRepositoryTest;
//import ru.otus.spring.domain.Book;
//import ru.otus.spring.domain.Genre;
//import ru.otus.spring.repository.BookRepository;
//import ru.otus.spring.repository.GenreRepository;
//
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static ru.otus.spring.util.TestUtils.HISTORICAL_BOOKS;
//import static ru.otus.spring.util.TestUtils.ID_1;
//
//@ComponentScan("ru.otus.spring.events")
//public class GenresRepositoryTest extends AbstractRepositoryTest {
//
//    @Autowired
//    private GenreRepository genreRepository;
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    private static final String FANTASY_GENRE = "Fantasy";
//
//
//    @Test
//    void successful_retrieval_by_id_test() {
//        Genre genre = genreRepository.findById(ID_1);
//        assertEquals(HISTORICAL_BOOKS, genre.getName());
//    }
//
//
//    @Test
//    void successful_addition_test() {
//        Genre genre = new Genre(null, RUSSIAN_CLASSIC);
//        genreDao.insert(genre);
//        Genre actualGenre = em.find(Genre.class, ID_3);
//        assertEquals(RUSSIAN_CLASSIC, actualGenre.getName());
//    }
//
//    @Test
//    void successful_removal_test() {
//        List<Book> books = bookRepository.findAll();
//        assertThat(books.stream().filter(book -> book.getGenres().stream().map(genre -> genre.get)));
//        genreRepository.deleteById(ID_1);
//        Assert.assertNull(em.find(Genre.class, ID_1));
//    }
//
//
//    @Test
//    void successful_update_test() {
//        Genre genre = new Genre(ID_3, FANTASY_GENRE);
//        genreDao.update(genre);
//        assertEquals(ID_3, (long) genreDao.getByName(FANTASY_GENRE).getId());
//    }
//}
