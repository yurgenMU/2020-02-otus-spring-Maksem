package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {

//    Genre findAuthorById(Long id);
//
//    Genre findGenreByName(String name);
//
//    @Query(value = "{'id' : $0}", delete = true)
//    void deleteById(Long id);
//
//    @Query(value = "{'name' : $0}", delete = true)
//    void deleteByName(String name);
//    void deleteGenreByName(String name);

    List<Genre> findAllByBooks(Book book);
}

