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
    public Genre findGenre(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Genre with this id does not exist in library"));
    }

    @Override
    public void addGenre(String name) {
        genreRepository.save(new Genre(null, name));
    }

    @Override
    public void editGenre(long id, String newName) {
        genreRepository.save(new Genre(id, newName));
    }

    @Override
    public List<Book> getAllBooksForGenre(long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Genre with this id not found"));
        return bookRepository.findAllByGenres(genre);
    }

    @Override
    public void removeGenre(long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public List<Genre> getAllGenres() {
        return (List<Genre>) genreRepository.findAll();
    }
}
