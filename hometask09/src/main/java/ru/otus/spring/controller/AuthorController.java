package ru.otus.spring.controller;

//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.AuthorService;

import java.util.List;


@Controller
public class AuthorController {

    private static final String AUTHORS = "authors";
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String loadPage(Model model) {
        model.addAttribute(AUTHORS, authorService.getAllAuthors());
        return AUTHORS;
    }
//
    @PostMapping("/authors")
    public String addAuthor(@RequestParam("name") String name) {
        authorService.addAuthor(name);
        return "redirect:/authors";
    }

    @GetMapping("/authors/edit/{id}")
    public String editAuthor(@PathVariable ("id") long id, Model model) {
        Author author = authorService.findAuthor(id);
        List<Book> allBooksForAuthor = authorService.getAllBooksForAuthor(id);
        model.addAttribute("author", author);
        model.addAttribute("books", allBooksForAuthor);
        return "editAuthor";
    }
//
//    @ShellMethod(value = "Edit existing author", key = {"edit author"})
//    public void editGenre(@ShellOption String identifier, @ShellOption String newName) {
//        authorService.editAuthor(identifier, newName);
//    }
//
//    @ShellMethod(value = "List of all authors of books in library", key = {"authors"})
//    public List<Author> getAllAuthors() {
//        return authorService.getAllAuthors();
//    }
//
//
//    @ShellMethod(value = "Describe author", key = {"describe author"})
//    public List<Book> getAllBookForGenre(@ShellOption String identifier) {
//        return authorService.getAllBooksForAuthor(identifier);
//    }
//
//    @ShellMethod(value = "Remove author", key = {"remove author"})
//    public void removeGenre(@ShellOption String identifier) {
//        authorService.removeAuthor(identifier);
//    }

}
