package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.util.LibraryException;

import java.util.List;

import static java.util.Objects.nonNull;
import static ru.otus.spring.util.LibraryUtils.isNumeric;
import static ru.otus.spring.util.LibraryUtils.retrieveEntity;

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
        Long id;
        if (isNumeric(identifier)) {
            id = Long.parseLong(identifier);
            genreRepository.save(new Genre(id, newName));
        } else {
            Genre genre = genreRepository.findGenreByName(identifier);
            if (nonNull(genre)) {
                id = genre.getId();
                genreRepository.save(new Genre(id, newName));
            }
        }
    }

    @Override
    public List<Book> getAllBooksForGenre(String identifier) {
        Genre genre = retrieveEntity(identifier, id -> genreRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Genre with this id not found")), genreRepository::findGenreByName);
        return bookRepository.findAllByGenres(genre);
    }

    @Override
    public void removeGenre(String identifier) {
        if (isNumeric(identifier)) {
            long id = Long.parseLong(identifier);
            genreRepository.deleteById(id);
        } else {
            genreRepository.deleteGenreByName(identifier);
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        return (List<Genre>) genreRepository.findAll();
    }
}
