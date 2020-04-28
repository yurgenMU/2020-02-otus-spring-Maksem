package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, Long> {

//    Book findBookById(Long id);
//
    Book findBookByName(String name);

//    @Query(value = "{'id' : $0}", delete = true)
//    void deleteById(Long id);
//
    @Query(value = "{'name' : $0}", delete = true)
    void deleteByName(String name);

    List<Book> findAllByAuthor(Author author);

    List<Book> findAllByGenres(Genre genre);
}
