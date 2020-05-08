package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static java.util.Objects.nonNull;
import static ru.otus.spring.util.LibraryUtils.isNumeric;
import static ru.otus.spring.util.LibraryUtils.processEntity;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;
    private final BookDao bookDao;

    public GenreServiceImpl(GenreDao genreDao, BookDao bookDao) {
        this.genreDao = genreDao;
        this.bookDao = bookDao;
    }

    @Override
    public void addGenre(String name) {
        genreDao.insert(new Genre(null, name));
    }

    @Override
    public void editGenre(String identifier, String newName) {
        Long id;
        if (isNumeric(identifier)) {
            id = Long.parseLong(identifier);
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
        Genre genre = processEntity(identifier, genreDao::getById, genreDao::getByName);
        return bookDao.getBooksByGenre(genre);
    }

    @Override
    public void removeGenre(String identifier) {
        if (isNumeric(identifier)) {
            long id = Long.parseLong(identifier);
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
