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
    public void addBook(String name, long authorId, List<String> genres) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new LibraryException("Author with this identifier does not exist in library"));
        List<Genre> genresList = retrieveGenres(genres);
        Book book = new Book(null, name, author, genresList);
        bookRepository.save(book);
    }

    @Override
    public Book getBook(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Book with this identifier does not exist in library"));
    }

    @Override
    public void updateBook(long id, String name, long authorId, List<String> genres) {
        Book book = getBook(id);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new LibraryException("Author with this identifier does not exist in library"));
        List<Genre> genresList = retrieveGenres(genres);
        bookRepository.save(new Book(book.getId(), name, author, genresList));
    }

    @Override
    public void removeBook(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    private List<Genre> retrieveGenres(List<String> genres) {
        return genres.stream().map(id -> genreRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new LibraryException("Genre with this identifier does not exist in library"))
        ).collect(Collectors.toList());
    }
}
