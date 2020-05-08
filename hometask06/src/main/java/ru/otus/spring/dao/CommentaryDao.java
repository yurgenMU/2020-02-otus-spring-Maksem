package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Commentary;

import java.util.List;

public interface CommentaryDao {

    void insert(Commentary commentary);

    Commentary getById(long id);

    List<Commentary> getCommentariesByBook(Book book);

    void updateCommentary(long id, String content);

    void deleteCommentary(long id);
}
