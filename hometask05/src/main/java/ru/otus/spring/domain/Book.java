package ru.otus.spring.domain;

import java.util.List;

public class Book {

    private final Integer id;
    private final String name;
    private final Author author;
    private final List<Genre> genres;

    public Book(Integer id, String name, Author author, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genres = genres;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }


    public List<Genre> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        var size = genres.size();
        String authorSignature = author != null ? "Author:" : "";
        String authorName = author != null ? author.getName() : "";
        String genreSignature = "";
        if (size > 1) {
            for (int i = 0; i < size - 1; i++) {
                sb.append(genres.get(i).getName()).append(", ");
            }
            sb.append(genres.get(size - 1).getName());
            genreSignature = "Genres:";
        } else {
            if (size == 1) {
                sb.append(genres.get(0).getName());
                genreSignature = "Genre:";
            }
        }
        return String.format("%s: %s. %s %s. %s %s", id, name, authorSignature, authorName, genreSignature, sb.toString()).trim();
    }
}
