package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;

//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.BookService;

import java.util.Arrays;

//
//import java.util.Arrays;
//import java.util.List;
//
@Controller("/books")
public class BookController {
    private static final String BOOKS = "books";

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String loadPage(Model model) {
        model.addAttribute(BOOKS, bookService.getAllBooks());
        return BOOKS;
    }

    //
    @PostMapping("/books/add")
    public void addGenre(@ModelAttribute("name") String name, @ModelAttribute("authorName") String authorName,
                         @ModelAttribute("genres") String genres) {
        bookService.addBook(name, authorName, Arrays.asList(genres.split(",")));
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
