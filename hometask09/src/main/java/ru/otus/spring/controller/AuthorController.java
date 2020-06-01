package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.AuthorsBooksDTO;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class AuthorController {

    private static final String AUTHORS = "authors";
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String loadPage(Model model) {
        List<Author> allAuthors = authorService.getAllAuthors();
        List<AuthorsBooksDTO> authorsBooks = allAuthors.stream()
                .map(author -> new AuthorsBooksDTO(author, authorService.getAllBooksForAuthor(author.getId())))
                .collect(Collectors.toList());
        model.addAttribute(AUTHORS, authorsBooks);
        return AUTHORS;
    }

    @GetMapping("/authors/new")
    public String loadNewAuthorPage() {
        return "addAuthor";
    }

    @PostMapping("/authors/new")
    public String addAuthor(@RequestParam("name") String name) {
        authorService.addAuthor(name);
        return "redirect:/authors";
    }

    @GetMapping("/authors/edit/{id}")
    public String loadEditAuthorPage(@PathVariable("id") long id, Model model) {
        Author author = authorService.findAuthor(id);
        List<Book> allBooksForAuthor = authorService.getAllBooksForAuthor(id);
        model.addAttribute("author", author);
        model.addAttribute("books", allBooksForAuthor);
        return "editAuthor";
    }

    @PostMapping("/authors/edit/{id}")
    public String editAuthor(@PathVariable("id") long id, @RequestParam("name") String name) {
        authorService.editAuthor(id, name);
        return "redirect:/authors";
    }

    @PostMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable("id") long id) {
        authorService.removeAuthor(id);
        return "redirect:/authors";
    }
}
