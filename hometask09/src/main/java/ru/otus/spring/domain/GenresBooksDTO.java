package ru.otus.spring.domain;

import java.util.List;

public class GenresBooksDTO {

    private Genre genre;
    private List<Book> books;

    public GenresBooksDTO(Genre genre, List<Book> books) {
        this.genre = genre;
        this.books = books;
    }

    public Genre getGenre() {
        return genre;
    }

    public List<Book> getBooks() {
        return books;
    }
}
