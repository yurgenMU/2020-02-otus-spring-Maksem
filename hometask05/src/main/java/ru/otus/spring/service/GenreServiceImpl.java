package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static java.util.Objects.nonNull;
import static ru.otus.spring.util.LibraryUtils.isNumeric;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public void addGenre(String name) {
        genreDao.insert(new Genre(null, name));
    }

    @Override
    public void editGenre(String identifier, String newName) {
        Integer id;
        if (isNumeric(identifier)) {
            id = Integer.parseInt(identifier);
            genreDao.update(new Genre(id, newName));
        } else {
            Genre genre = genreDao.getByName(identifier);
            if (nonNull(genre)) {
                id = genre.getId();
                genreDao.update(new Genre(id, newName));
            }
        }
    }

    @Override
    public List<Book> getAllBooksForGenre(String identifier) {
        Genre genre;
        if (isNumeric(identifier)) {
            int id = Integer.parseInt(identifier);
            genre = new Genre(id, null);
        } else {
            genre = new Genre(null, identifier);
        }
        return genreDao.getBooksByGenre(genre);
    }

    @Override
    public void removeGenre(String identifier) {
        if (isNumeric(identifier)) {
            int id = Integer.parseInt(identifier);
            genreDao.deleteById(id);
        } else {
            genreDao.deleteByName(identifier);
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }
}
