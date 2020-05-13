package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentaryRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Commentary;
import ru.otus.spring.util.LibraryException;

import java.util.List;

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
        Book book = bookRepository.findById(bookIdentifier).orElseThrow(
                () -> new LibraryException("Book with this id not found"));
        Commentary commentary = new Commentary(content, book);
        commentaryRepository.save(commentary);
    }

    @Override
    public void updateCommentary(String commentaryIdentifier, String content) {
        commentaryRepository.insert(new Commentary(commentaryIdentifier, content));
    }

    @Override
    public void deleteCommentary(String commentaryIdentifier) {
        commentaryRepository.deleteById(commentaryIdentifier);
    }

    @Override
    public List<Commentary> getAllCommentaries(String bookIdentifier) {
        Book book = bookRepository.findById(bookIdentifier).orElseThrow(
                () -> new LibraryException("Book with this id not found"));
        return commentaryRepository.findAllByBook(book);
    }
}
