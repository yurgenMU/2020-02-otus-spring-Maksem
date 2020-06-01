package ru.otus.spring.service;

import ru.otus.spring.domain.Commentary;

import java.util.List;

public interface CommentaryService {

    Commentary find(long id);
    
    void addCommentaryToBook(long bookId, String content);

    void updateCommentary(long id, String content);

    void deleteCommentary(long id);

    List<Commentary> getAllCommentaries(long bookId);
}
