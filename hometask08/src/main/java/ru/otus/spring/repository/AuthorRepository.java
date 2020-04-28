package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, Long> {

    Author findByName(String name);

//    @Query(value = "{'id' : $0}", delete = true)
//    void deleteById(Long id);
//
    @Query(value = "{'name' : $0}", delete = true)
    void deleteByName(String name);


}
