package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.util.LibraryException;

import java.util.List;


@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Author findAuthor(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Author with this id does not exist in library"));
    }

    @Override
    public void addAuthor(String name) {
        authorRepository.save(new Author(null, name));
    }

    @Override
    public void editAuthor(long id, String newName) {
        authorRepository.save(new Author(id, newName));
    }

    @Override
    public List<Book> getAllBooksForAuthor(long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Author with this identifier not found"));
        return bookRepository.findAllByAuthor(author);
    }

    @Override
    public void removeAuthor(long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }
}
