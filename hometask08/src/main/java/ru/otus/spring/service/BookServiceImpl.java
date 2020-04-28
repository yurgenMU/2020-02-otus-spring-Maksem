package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.util.LibraryException;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static ru.otus.spring.util.LibraryUtils.isNumeric;
import static ru.otus.spring.util.LibraryUtils.retrieveEntity;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public void addBook(String name, String authorIdentifier, List<String> genres) {
        Author author = retrieveAuthor(authorIdentifier);
        List<Genre> genresList = retrieveGenres(genres);
        Book book = new Book(null, name, author, genresList);
        bookRepository.save(book);
    }

    @Override
    public Book getBook(String identifier) {
        return retrieveEntity(identifier, id -> bookRepository.findById(id).orElseThrow(), bookRepository::findBookByName);
    }

    @Override
    public void updateBook(String bookIdentifier, String name, String authorIdentifier, List<String> genres) {
        Book book = retrieveEntity(bookIdentifier, id -> bookRepository.findById(id).orElseThrow(), bookRepository::findBookByName);
        if (isNull(book)) {
            throw new LibraryException("Book with this identifier does not exist in library");
        }
        Author author = retrieveAuthor(authorIdentifier);
        List<Genre> genresList = retrieveGenres(genres);
        bookRepository.save(new Book(book.getId(), name, author, genresList));
    }

    @Override
    public void removeBook(String identifier) {
        if (isNumeric(identifier)) {
            long id = Long.parseLong(identifier);
            bookRepository.deleteById(id);
        } else {
            bookRepository.deleteByName(identifier);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    private Author retrieveAuthor(String authorIdentifier) {
        Author author;
        if (isNumeric(authorIdentifier)) {
            long authorId = Long.parseLong(authorIdentifier);
            author = authorRepository.findById(authorId).orElseThrow(BookServiceImpl::notFoundSupplier);
        } else {
            author = authorRepository.findByName(authorIdentifier);
        }
        if (isNull(author)) {
            throw new LibraryException("Author with this identifier does not exist in library");
        }
        return author;
    }

    private List<Genre> retrieveGenres(List<String> genres) {
        return genres.stream().map(genreIdentifier -> {
            Genre genre = retrieveEntity(genreIdentifier, id -> genreRepository.findById(id)
                    .orElseThrow(BookServiceImpl::notFoundSupplier), genreRepository::findGenreByName);
            if (isNull(genre)) {
                throw new LibraryException("Genre with this identifier does not exist in library");
            }
            return genre;
        }).collect(Collectors.toList());
    }

    private static LibraryException notFoundSupplier() {
        return new LibraryException("Genre with this identifier not found");
    }

}
