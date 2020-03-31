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

    private Book retrieveBook(String bookIdentifier) {
        Book book;
        if (isNumeric(bookIdentifier)) {
            int bookId = Integer.parseInt(bookIdentifier);
            book = bookDao.getById(bookId);
        } else {
            book = bookDao.getByName(bookIdentifier);
        }
        return book;
    }

    @Override
    public void removeBook(String identifier) {
        if (isNumeric(identifier)) {
            int id = Integer.parseInt(identifier);
            bookDao.deleteById(id);
        } else {
            bookDao.deleteByName(identifier);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    private Author retrieveAuthor(String authorIdentifier) {
        Author author;
        if (isNumeric(authorIdentifier)) {
            int authorId = Integer.parseInt(authorIdentifier);
            author = authorDao.getById(authorId);
        } else {
            author = authorDao.getByName(authorIdentifier);
        }
        if (isNull(author)) {
            throw new LibraryException("Author with this identifier does not exist in library");
        }
        return author;
    }

    private List<Genre> retrieveGenres(List<String> genres) {
        return genres.stream().map(genreIdentifier -> {
            Genre genre;
            if (isNumeric(genreIdentifier)) {
                int genreId = Integer.parseInt(genreIdentifier);
                genre = genreDao.getById(genreId);
            } else {
                genre = genreDao.getByName(genreIdentifier);
            }
            if (isNull(genre)) {
                throw new LibraryException("Genre with this identifier does not exist in library");
            }
            return genre;
        }).collect(Collectors.toList());
    }

}
