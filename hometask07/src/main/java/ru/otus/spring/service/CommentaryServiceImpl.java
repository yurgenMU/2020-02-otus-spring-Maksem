package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentaryRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Commentary;
import ru.otus.spring.util.LibraryException;

import java.util.List;

import static ru.otus.spring.util.LibraryUtils.retrieveEntity;

@Service
public class CommentaryServiceImpl implements CommentaryService {

    private final CommentaryRepository commentaryRepository;
    private final BookRepository bookRepository;

    public CommentaryServiceImpl(CommentaryRepository commentaryRepository, BookRepository bookRepository) {
        this.commentaryRepository = commentaryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void addCommentaryToBook(String bookIdentifier, String content) {
        Book book = retrieveEntity(bookIdentifier, bookRepository::retrieveById, bookRepository::findByName);
        if (book == null) {
            throw new LibraryException("Book with this identifier does not exist");
        }
        Commentary commentary = new Commentary(content, book);
        commentaryRepository.save(commentary);
    }

    @Override
    public void updateCommentary(String commentaryIdentifier, String content) {
        long id = Long.parseLong(commentaryIdentifier);
        commentaryRepository.updateCommentary(id, content);
    }

    @Override
    public void deleteCommentary(String commentaryIdentifier) {
        long id = Long.parseLong(commentaryIdentifier);
        commentaryRepository.deleteById(id);
    }

    @Override
    public List<Commentary> getAllCommentaries(String bookIdentifier) {
        Book book = retrieveEntity(bookIdentifier, bookRepository::retrieveById, bookRepository::findByName);
        if (book == null) {
            throw new LibraryException("Book with this identifier does not exist");
        }
        return commentaryRepository.findAllByBook(book);
    }
}
