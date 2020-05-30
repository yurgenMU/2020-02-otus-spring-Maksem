package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;

//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import java.util.Arrays;

//
//import java.util.Arrays;
//import java.util.List;
//
@Controller
public class BookController {
    private static final String BOOKS = "books";

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/books")
    public String loadPage(Model model) {
        model.addAttribute(BOOKS, bookService.getAllBooks());
        return BOOKS;
    }

    //
    @PostMapping("/books/add")
    public void addGenre(@RequestParam("name") String name, @RequestParam("authorName") String authorName,
                         @RequestParam("genres") String genres) {
        bookService.addBook(name, authorName, Arrays.asList(genres.split(",")));
    }

    @GetMapping("/books/edit/{id}")
    public String loadEditBooksPage(@PathVariable("id") String id, Model model) {
        model.addAttribute("book", bookService.getBook(id));
        model.addAttribute("allAuthors", authorService.getAllAuthors());
        model.addAttribute("allGenres", genreService.getAllGenres());
        return "editBook";
    }

    @PostMapping("/books/edit/{id}")
    public String editBook(@PathVariable("id") String id, @RequestParam("name") String name, @RequestParam("author") String author,
                           @RequestParam("genres") String genres) {

        return "redirect:/books";
    }
//
//    @ShellMethod(value = "Edit existing book", key = {"edit book"})
//    public void editGenre(@ShellOption String bookIdentifier,
//                          @ShellOption String name, @ShellOption String authorName, @ShellOption String genres) {
//        System.out.println(Arrays.toString(genres.split(",")));
//        bookService.updateBook(bookIdentifier, name, authorName, Arrays.asList(genres.split(",")));
//    }
//
//    @ShellMethod(value = "Get book by name or id", key = {"describe book"})
//    public Book getBook(@ShellOption String bookIdentifier) {
//        return bookService.getBook(bookIdentifier);
//    }
//
//    @ShellMethod(value = "List of all authors of books in library", key = {"books"})
//    public List<Book> getAllBooks() {
//        return bookService.getAllBooks();
//    }
//
//    @ShellMethod(value = "Remove book", key = {"remove book"})
//    public void getAllBookForGenre(@ShellOption String identifier) {
//        bookService.removeBook(identifier);
//    }
}
