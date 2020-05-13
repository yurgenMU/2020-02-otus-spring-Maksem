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
        return bookRepository.findById(identifier).orElseThrow(
                () -> new LibraryException("Book with this identifier does not exist in library"));
    }

    @Override
    public void updateBook(String bookIdentifier, String name, String authorIdentifier, List<String> genres) {
        Book book = bookRepository.findById(bookIdentifier).orElseThrow(
                () -> new LibraryException("Book with this identifier does not exist in library")
        );
        Author author = retrieveAuthor(authorIdentifier);
        List<Genre> genresList = retrieveGenres(genres);
        bookRepository.save(new Book(book.getId(), name, author, genresList));
    }

    @Override
    public void removeBook(String identifier) {
        bookRepository.deleteById(identifier);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    private Author retrieveAuthor(String authorIdentifier) {
        return authorRepository.findById(authorIdentifier).orElseThrow(
                () -> new LibraryException("Author with this identifier does not exist in library"));
    }

    private List<Genre> retrieveGenres(List<String> genres) {
        return genres.stream().map(genreIdentifier -> genreRepository.findById(genreIdentifier).orElseThrow(
                () -> new LibraryException("Genre with this identifier does not exist in library")))
                .collect(Collectors.toList());
    }

}
