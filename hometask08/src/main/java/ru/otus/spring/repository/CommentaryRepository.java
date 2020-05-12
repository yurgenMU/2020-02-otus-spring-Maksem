package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Commentary;

import java.util.List;

public interface CommentaryRepository extends MongoRepository<Commentary, String> {




//    @Query(value = "{'id' : $0}", delete = true)
//    void deleteById(Long id);
//
//    @Query(value = "{'id' : $0}", delete = )
//    void updateCommentary(Long id, String content);

    List<Commentary> findAllByBook(Book book);

}
