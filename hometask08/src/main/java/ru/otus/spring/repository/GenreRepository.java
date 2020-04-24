package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, Long> {

    Genre findAuthorById(Long id);

    Genre findByName(String name);

    @Query(value = "{'id' : $0}", delete = true)
    void deleteById(Long id);

    @Query(value = "{'name' : $0}", delete = true)
    void deleteByName(String name);

    List<Genre> findAllByBook(Book book);
}

