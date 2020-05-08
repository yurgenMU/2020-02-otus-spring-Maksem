package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Commentary;

import java.util.List;

public interface CommentaryRepository extends CrudRepository<Commentary, Long> {

    List<Commentary> findAllByBook(Book book);

    @Transactional
    @Modifying
    @Query("update Commentary c set c.content = :content where c.id = :id")
    void updateCommentary(@Param("id") Long id, @Param("content") String content);

}
