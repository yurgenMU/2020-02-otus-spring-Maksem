package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Commentary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@Transactional
@Repository
public class CommentaryDaoJpa implements CommentaryDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public CommentaryDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void insert(Commentary commentary) {
        entityManager.merge(commentary);
    }

    @Override
    public Commentary getById(long id) {
        return entityManager.find(Commentary.class, id);
    }

    @Override
    public List<Commentary> getCommentariesByBook(Book book) {
        TypedQuery<Commentary> query = entityManager.createQuery("select c from Commentary c where " +
                "c.book = :book", Commentary.class)
                .setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public void updateCommentary(long id, String content) {
        Query query = entityManager.createQuery("update Commentary c " +
                "set c.content = :content " +
                "where c.id = :id");
        query.setParameter("content", content);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteCommentary(long id) {
        entityManager.createQuery("delete " +
                "from Commentary c " +
                "where c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
