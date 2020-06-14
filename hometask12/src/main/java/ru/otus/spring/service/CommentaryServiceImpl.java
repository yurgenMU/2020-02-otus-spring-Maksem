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
    public Commentary find(long id) {
        return commentaryRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Commentary with this identifier does not exist"));
    }

    @Override
    public void addCommentaryToBook(long bookId, String content) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryException("Book with this identifier does not exist"));
        Commentary commentary = new Commentary(content, book);
        commentaryRepository.save(commentary);
    }

    @Override
    public void updateCommentary(long commentaryId, String content) {
        commentaryRepository.updateCommentary(commentaryId, content);
    }

    @Override
    public void deleteCommentary(long commentaryId) {
        commentaryRepository.deleteById(commentaryId);
    }

    @Override
    public List<Commentary> getAllCommentaries(long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryException("Book with this identifier does not exist"));
        return commentaryRepository.findAllByBook(book);
    }
}
