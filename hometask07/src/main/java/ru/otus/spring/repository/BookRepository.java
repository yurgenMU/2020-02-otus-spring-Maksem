package ru.otus.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.util.LibraryException;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    Book findByName(String name);

    void deleteByName(String name);

    List<Book> findAllByGenres(Genre genre);

    List<Book> findAllByAuthor(Author author);

    default Book retrieveById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new LibraryException("Book with this identifier not found"));
    }

}
