package ru.otus.spring.service;

import ru.otus.spring.domain.Commentary;

import java.util.List;

public interface CommentaryService {
    
    void addCommentaryToBook(String bookIdentifier, String content);

    void updateCommentary(String commentaryIdentifier, String content);

    void deleteCommentary(String commentaryIdentifier);

    List<Commentary> getAllCommentaries(String bookIdentifier);
}
