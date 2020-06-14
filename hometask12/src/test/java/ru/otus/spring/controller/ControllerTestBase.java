package ru.otus.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Commentary;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentaryService;
import ru.otus.spring.service.GenreService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public abstract class ControllerTestBase {

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentaryService commentaryService;

    @BeforeEach
    public void unit() {
        when(authorService.getAllAuthors()).thenReturn(Collections.EMPTY_LIST);
        when(authorService.findAuthor(anyLong())).thenReturn(new Author());
        when(authorService.getAllBooksForAuthor(anyLong())).thenReturn(Collections.EMPTY_LIST);
        when(genreService.getAllBooksForGenre(anyLong())).thenReturn(Collections.EMPTY_LIST);
        when(genreService.getAllGenres()).thenReturn(Collections.EMPTY_LIST);
        when(genreService.findGenre(anyLong())).thenReturn(new Genre());
        when(commentaryService.getAllCommentaries(anyLong())).thenReturn(Collections.EMPTY_LIST);
        when(commentaryService.find(anyLong())).thenReturn(new Commentary(null, new Book()));
        when(bookService.getAllBooks()).thenReturn(Collections.EMPTY_LIST);
        when(bookService.getBook(anyLong())).thenReturn(new Book());
    }
}
