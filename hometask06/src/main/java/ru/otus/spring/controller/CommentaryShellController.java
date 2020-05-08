package ru.otus.spring.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Commentary;
import ru.otus.spring.service.CommentaryService;

import java.util.List;

@ShellComponent
public class CommentaryShellController {

    private final CommentaryService commentaryService;

    public CommentaryShellController(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @ShellMethod(value = "Add commentary to book", key = {"add commentary"})
    public void addCommentaryToBook(@ShellOption String bookIdentifier, @ShellOption String content) {
        commentaryService.addCommentaryToBook(bookIdentifier, content);
    }

    @ShellMethod(value = "Edit existing commentary", key = {"edit commentary"})
    public void editCommentary(@ShellOption String commentaryIdentifier,
                               @ShellOption String content) {
        commentaryService.updateCommentary(commentaryIdentifier, content);
    }

    @ShellMethod(value = "Delete commentary", key = {"delete commentary"})
    public void deleteCommentary(@ShellOption String commentaryIdentifier) {
        commentaryService.deleteCommentary(commentaryIdentifier);
    }

    @ShellMethod(value = "List of commentaries for selected book", key = {"book commentaries"})
    public List<Commentary> getAllCommentariesForBook(String bookIdentifier) {
        return commentaryService.getAllCommentaries(bookIdentifier);
    }

}
