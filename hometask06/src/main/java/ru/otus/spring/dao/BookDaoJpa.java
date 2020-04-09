package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void insert(Book book) {
        entityManager.merge(book);
    }

    @Override
    public Book getById(long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public Book getByName(String name) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b where b.name = :name", Book.class)
                .setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b inner join b.genres g  where g.id = :genre_id", Book.class)
                .setParameter("genre_id", genre.getId());
        return query.getResultList();
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b where b.author = :author", Book.class)
                .setParameter("author", author);
        return query.getResultList();
    }

    @Override
    public void update(Book book) {
        entityManager.merge(book);
    }

    @Override
    public void deleteByName(String name) {
        entityManager.createQuery("delete " +
                "from Book b " +
                "where b.name = :name")
        .setParameter("name", name)
        .executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        entityManager.createQuery("delete " +
                "from Book b " +
                "where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Book> getAll() {
        EntityGraph graph = entityManager.getEntityGraph("books-entity-graph");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", graph);
        return query.getResultList();
    }

}
