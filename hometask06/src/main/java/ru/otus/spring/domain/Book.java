package ru.otus.spring.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedEntityGraph(name = "books-entity-graph",
        attributeNodes = {@NamedAttributeNode("genres"), @NamedAttributeNode("author")})
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(targetEntity = Author.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "genres_books", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    public Book() {
    }

    public Book(Long id, String name, Author author, List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genres = genres;
    }

    public Long getId() {
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
