package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentaryService;

@Controller
public class CommentariesController {

    private final BookService bookService;
    private final CommentaryService commentaryService;

    public CommentariesController(BookService bookService, CommentaryService commentaryService) {
        this.bookService = bookService;
        this.commentaryService = commentaryService;
    }

    @GetMapping("/commentaries/new/{bookId}")
    public String loadAddCommentaryPage(@PathVariable("bookId") long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "addComment";
    }

    @PostMapping("/commentaries/new/{bookId}")
    public String addCommentaryToBook(@PathVariable("bookId") long bookId, @RequestParam("content") String commentaryContent) {
        commentaryService.addCommentaryToBook(bookId, commentaryContent);
        return "redirect:/books/edit/" + bookId;
    }

    @GetMapping("/commentaries/edit/{id}")
    public String loadEditCommentaryPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("comment", commentaryService.find(id));
        return "editComment";
    }

    @PostMapping("/commentaries/edit/{id}")
    public String editCommentary(@PathVariable("id") long id, @RequestParam("bookId") long bookId,
                                 @RequestParam("content") String commentaryContent) {
        commentaryService.updateCommentary(id, commentaryContent);
        return "redirect:/books/edit/" + bookId;
    }

    @PostMapping("/commentaries/delete/{id}")
    public String deleteCommentary(@PathVariable("id") long id, @RequestParam("bookId") long bookId) {
        commentaryService.deleteCommentary(id);
        return "redirect:/books/edit/" + bookId;
    }
}
