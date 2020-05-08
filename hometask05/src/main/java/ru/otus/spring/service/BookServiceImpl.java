package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.util.LibraryException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static ru.otus.spring.util.LibraryUtils.isNumeric;
import static ru.otus.spring.util.LibraryUtils.processEntity;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public void addBook(String name, String authorIdentifier, List<String> genres) {
        Author author = retrieveAuthor(authorIdentifier);
        List<Genre> genresList = retrieveGenres(genres);
        Book book = new Book(null, name, author, genresList);
        bookDao.insert(book);
    }

    @Override
    public Book getBook(String identifier) {
        return retrieveBook(identifier);
    }

    @Override
    public void updateBook(String bookIdentifier, String name, String authorIdentifier, List<String> genres) {
        Book book = retrieveBook(bookIdentifier);
        if (isNull(book)) {
            throw new LibraryException("Book with this identifier does not exist in library");
        }
        Author author = retrieveAuthor(authorIdentifier);
        List<Genre> genresList = retrieveGenres(genres);
        bookDao.update(new Book(book.getId(), name, author, genresList));
    }

    @Override
    public void removeBook(String identifier) {
        if (isNumeric(identifier)) {
            long id = Long.parseLong(identifier);
            bookDao.deleteById(id);
        } else {
            bookDao.deleteByName(identifier);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    private Book retrieveBook(String bookIdentifier) {
        return processEntity(bookIdentifier, bookDao::getById, bookDao::getByName);
    }

    private Author retrieveAuthor(String authorIdentifier) {
        Author author = processEntity(authorIdentifier, authorDao::getById, authorDao::getByName);
        if (isNull(author)) {
            throw new LibraryException("Author with this identifier does not exist in library");
        }
        return author;
    }

    private List<Genre> retrieveGenres(List<String> genres) {
        return genres.stream().map(genreIdentifier -> {
            Genre genre = processEntity(genreIdentifier, genreDao::getById, genreDao::getByName);
            if (isNull(genre)) {
                throw new LibraryException("Genre with this identifier does not exist in library");
            }
            return genre;
        }).collect(Collectors.toList());
    }

}
