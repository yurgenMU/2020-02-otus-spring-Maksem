package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.util.LibraryException;

import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAllByGenres(Genre genre);

    @EntityGraph("books-entity-graph")
    List<Book> findAllByAuthor(Author author);

    @EntityGraph("books-entity-graph")
    Iterable<Book> findAll();

}
