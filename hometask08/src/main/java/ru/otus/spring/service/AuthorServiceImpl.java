package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.util.LibraryException;

import java.util.List;

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
        authorRepository.save(new Author(identifier, newName));
    }

    @Override
    public List<Book> getAllBooksForAuthor(String identifier) {
        Author author = authorRepository.findById(identifier).orElseThrow(
                () -> new LibraryException("Author with this identifier does not exist in library")
        );
        return bookRepository.findAllByAuthor(author);
    }

    @Override
    public void removeAuthor(String identifier) {
        authorRepository.deleteById(identifier);
    }

    @Override
    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }
}
