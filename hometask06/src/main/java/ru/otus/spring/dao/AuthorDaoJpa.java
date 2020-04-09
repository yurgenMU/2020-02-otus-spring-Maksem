package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;

import javax.persistence.*;
import java.util.List;

@Transactional
@Repository
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public AuthorDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void insert(Author author) {
        entityManager.persist(author);
    }

    @Override
    public Author getById(long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = entityManager.createQuery("select a from Author a where a.name = :name", Author.class)
                .setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void update(Author author) {
        entityManager.createQuery("update Author a set a.name = :name where a.id = :id")
                .setParameter("name", author.getName())
                .setParameter("id", author.getId())
                .executeUpdate();
    }

    @Override
    public void deleteByName(String name) {
        entityManager.createQuery("delete from Author a where a.name = :name")
                .setParameter("name", name)
                .executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        entityManager.createQuery("delete from Author a where a.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Author> getAll() {
        return entityManager.createQuery("select a from Author a", Author.class)
                .getResultList();
    }
}
