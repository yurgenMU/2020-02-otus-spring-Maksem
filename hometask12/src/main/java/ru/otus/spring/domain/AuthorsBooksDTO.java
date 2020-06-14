package ru.otus.spring.domain;

import java.util.List;

public class AuthorsBooksDTO {

    private Author author;
    private List<Book> books;

    public AuthorsBooksDTO(Author author, List<Book> books) {
        this.author = author;
        this.books = books;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Book> getBooks() {
        return books;
    }
}
