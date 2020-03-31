package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

import static java.util.Objects.nonNull;
import static ru.otus.spring.util.LibraryUtils.isNumeric;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public void addAuthor(String name) {
        authorDao.insert(new Author(null, name));
    }

    @Override
    public void editAuthor(String identifier, String newName) {
        Integer id;
        if (isNumeric(identifier)) {
            id = Integer.parseInt(identifier);
            authorDao.update(new Author(id, newName));
        } else {
            Author author = authorDao.getByName(identifier);
            if (nonNull(author)) {
                id = author.getId();
                authorDao.update(new Author(id, newName));
            }
        }
    }

    @Override
    public List<Book> getAllBooksForAuthor(String identifier) {
        Author author;
        if (isNumeric(identifier)) {
            int id = Integer.parseInt(identifier);
            author = new Author(id, null);
        } else {
            author = new Author(null, identifier);
        }
        return authorDao.getBooksByAuthor(author);
    }

    @Override
    public void removeAuthor(String identifier) {
        if (isNumeric(identifier)) {
            int id = Integer.parseInt(identifier);
            authorDao.deleteById(id);
        } else {
            authorDao.deleteByName(identifier);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }
}
