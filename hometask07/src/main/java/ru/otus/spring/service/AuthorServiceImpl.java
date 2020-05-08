package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.util.LibraryException;

import java.util.List;

import static java.util.Objects.nonNull;
import static ru.otus.spring.util.LibraryUtils.isNumeric;
import static ru.otus.spring.util.LibraryUtils.retrieveEntity;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void addAuthor(String name) {
        authorRepository.save(new Author(null, name));
    }

    @Override
    public void editAuthor(String identifier, String newName) {
        Long id;
        if (isNumeric(identifier)) {
            id = Long.parseLong(identifier);
            authorRepository.save(new Author(id, newName));
        } else {
            Author author = authorRepository.findByName(identifier);
            if (nonNull(author)) {
                id = author.getId();
                authorRepository.save(new Author(id, newName));
            }
        }
    }

    @Override
    public List<Book> getAllBooksForAuthor(String identifier) {
        Author author = retrieveEntity(identifier, id -> authorRepository.findById(id)
                        .orElseThrow(() -> new LibraryException("Author with this identifier not found")),
                authorRepository::findByName);
        return bookRepository.findAllByAuthor(author);
    }

    @Override
    public void removeAuthor(String identifier) {
        if (isNumeric(identifier)) {
            long id = Long.parseLong(identifier);
            authorRepository.deleteById(id);
        } else {
            authorRepository.deleteByName(identifier);
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }
}
