package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public GenreDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void insert(Genre genre) {
        entityManager.persist(genre);
    }

    @Override
    public Genre getById(long id) {
        return entityManager.find(Genre.class, id);

    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("select g from Genre g where g.name = :name", Genre.class)
                .setParameter("name", name);
        return query.getSingleResult();

    }

    @Override
    public void update(Genre genre) {
        Query query = entityManager.createQuery("update Genre g " +
                "set g.name = :name " +
                "where g.id = :id");
        query.setParameter("name", genre.getName());
        query.setParameter("id", genre.getId());
        query.executeUpdate();
    }

    @Override
    public void deleteByName(String name) {
        Query query = entityManager.createQuery("delete " +
                "from Genre g " +
                "where g.name = :name");
        query.setParameter("name", name);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query query = entityManager.createQuery("delete " +
                "from Genre g " +
                "where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Genre> getAll() {
        return entityManager.createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }

}
