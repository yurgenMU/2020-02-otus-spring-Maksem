package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.util.LibraryException;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public GenreServiceImpl(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void addGenre(String name) {
        genreRepository.save(new Genre(null, name));
    }

    @Override
    public void editGenre(String identifier, String newName) {
        genreRepository.save(new Genre(identifier, newName));
    }

    @Override
    public List<Book> getAllBooksForGenre(String identifier) {
        Genre genre = genreRepository.findById(identifier)
                .orElseThrow(() -> new LibraryException("Genre with this id not found"));
        return bookRepository.findAllByGenres(genre);
    }

    @Override
    public void removeGenre(String identifier) {
        genreRepository.deleteById(identifier);
    }

    @Override
    public List<Genre> getAllGenres() {
        return (List<Genre>) genreRepository.findAll();
    }
}
