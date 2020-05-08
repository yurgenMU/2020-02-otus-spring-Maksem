package ru.otus.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Genre findByName(String name);

    void deleteByName(String name);

}
