package ru.otus.spring.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "commentaries")
public class Commentary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(targetEntity = Book.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "book_id")
    private Book book;

    public Commentary(String content, Book book) {
        this.content = content;
        this.book = book;
        this.date = new Date();
    }

    public Commentary() {

    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return String.format("%s: %s \"%s\"", id, date, content);
    }
}
