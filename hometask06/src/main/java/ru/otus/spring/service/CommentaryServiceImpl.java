package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.CommentaryDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Commentary;
import ru.otus.spring.util.LibraryException;

import java.util.List;

import static ru.otus.spring.util.LibraryUtils.retrieveEntity;

@Service
public class CommentaryServiceImpl implements CommentaryService {

    private final CommentaryDao commentaryDao;
    private final BookDao bookDao;

    public CommentaryServiceImpl(CommentaryDao commentaryDao, BookDao bookDao) {
        this.commentaryDao = commentaryDao;
        this.bookDao = bookDao;
    }

    @Override
    public void addCommentaryToBook(String bookIdentifier, String content) {
        Book book = retrieveEntity(bookIdentifier, bookDao::getById, bookDao::getByName);
        if (book == null) {
            throw new LibraryException("Book with this identifier does not exist");
        }
        Commentary commentary = new Commentary(content, book);
        commentaryDao.insert(commentary);
    }

    @Override
    public void updateCommentary(String commentaryIdentifier, String content) {
        long id = Long.parseLong(commentaryIdentifier);
        commentaryDao.updateCommentary(id, content);
    }

    @Override
    public void deleteCommentary(String commentaryIdentifier) {
        long id = Long.parseLong(commentaryIdentifier);
        commentaryDao.deleteCommentary(id);
    }

    @Override
    public List<Commentary> getAllCommentaries(String bookIdentifier) {
        Book book = retrieveEntity(bookIdentifier, bookDao::getById, bookDao::getByName);
        if (book == null) {
            throw new LibraryException("Book with this identifier does not exist");
        }
        return commentaryDao.getCommentariesByBook(book);
    }
}
