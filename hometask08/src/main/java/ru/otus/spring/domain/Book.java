package ru.otus.spring.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String name;

    @DBRef
    private Author author;

    @DBRef
    private List<Genre> genres;

    public Book() {
    }

    public Book(String id, String name, Author author, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genres = genres;
    }

    public String getId() {
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
