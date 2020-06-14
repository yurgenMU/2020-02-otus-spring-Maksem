package ru.otus.spring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.GenresBooksDTO;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GenreController {

    private static final String GENRES = "genres";
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String loadPage(Model model) {
        List<Genre> allGenres = genreService.getAllGenres();
        List<GenresBooksDTO> genresBooks = allGenres.stream()
                .map(genre -> new GenresBooksDTO(genre, genreService.getAllBooksForGenre(genre.getId())))
                .collect(Collectors.toList());
        model.addAttribute(GENRES, genresBooks);
        return GENRES;
    }

    @GetMapping("/genres/new")
    public String loadNewGenrePage() {
        return "addGenre";
    }

    @PostMapping("/genres/new")
    public String addGenre(@RequestParam("name") String name) {
        genreService.addGenre(name);
        return "redirect:/genres";
    }

    @GetMapping("/genres/edit/{id}")
    public String loadEditGenrePage(@PathVariable("id") long id, Model model) {
        Genre genre = genreService.findGenre(id);
        List<Book> allBooksForGenre = genreService.getAllBooksForGenre(id);
        model.addAttribute("genre", genre);
        model.addAttribute("books", allBooksForGenre);
        return "editGenre";
    }

    @PostMapping("/genres/edit/{id}")
    public String editGenre(@PathVariable("id") long id, @RequestParam("name") String name) {
        genreService.editGenre(id, name);
        return "redirect:/genres";
    }

    @PostMapping("/genres/delete/{id}")
    public String deleteGenre(@PathVariable("id") long id) {
        genreService.removeGenre(id);
        return "redirect:/genres";
    }
}
