package ru.otus.spring.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.AuthorService;

import java.util.List;


@ShellComponent
public class AuthorShellController {

    private final AuthorService authorService;

    public AuthorShellController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod(value = "Add new author", key = {"add author"})
    public void addGenre(@ShellOption String name) {
        authorService.addAuthor(name);
    }

    @ShellMethod(value = "Edit existing author", key = {"edit author"})
    public void editGenre(@ShellOption String identifier, @ShellOption String newName) {
        authorService.editAuthor(identifier, newName);
    }

    @ShellMethod(value = "List of all authors of books in library", key = {"authors"})
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }


    @ShellMethod(value = "Describe author", key = {"describe author"})
    public List<Book> getAllBookForGenre(@ShellOption String identifier) {
        return authorService.getAllBooksForAuthor(identifier);
    }

    @ShellMethod(value = "Remove author", key = {"remove author"})
    public void removeGenre(@ShellOption String identifier) {
        authorService.removeAuthor(identifier);
    }

}
