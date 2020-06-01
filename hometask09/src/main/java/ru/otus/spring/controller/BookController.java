package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentaryService;
import ru.otus.spring.service.GenreService;

import java.util.Arrays;

@Controller
public class BookController {
    private static final String BOOKS = "books";

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentaryService commentaryService;

    public BookController(BookService bookService, AuthorService authorService,
                          GenreService genreService, CommentaryService commentaryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentaryService = commentaryService;
    }

    @GetMapping("/books")
    public String loadPage(Model model) {
        model.addAttribute(BOOKS, bookService.getAllBooks());
        return BOOKS;
    }

    //
    @GetMapping("/books/new")
    public String loadNewBookPage(Model model) {
        model.addAttribute("allAuthors", authorService.getAllAuthors());
        model.addAttribute("allGenres", genreService.getAllGenres());
        return "addBook";
    }

    @PostMapping("/books/new")
    public String addNewBook(@RequestParam("name") String name, @RequestParam("authorId") long authorId,
                             @RequestParam("genres") String genres) {
        bookService.addBook(name, authorId, Arrays.asList(genres.split(",")));
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String loadEditBookPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("book", bookService.getBook(id));
        model.addAttribute("allAuthors", authorService.getAllAuthors());
        model.addAttribute("allGenres", genreService.getAllGenres());
        model.addAttribute("comments", commentaryService.getAllCommentaries(id));
        return "editBook";
    }

    @PostMapping("/books/edit/{id}")
    public String editBook(@PathVariable("id") long id, @RequestParam("name") String name,
                           @RequestParam("authorId") long authorId,
                           @RequestParam("genres") String genres) {
        bookService.updateBook(id, name, authorId, Arrays.asList(genres.split(",")));
        return "redirect:/books";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.removeBook(id);
        return "redirect:/books";
    }
}
