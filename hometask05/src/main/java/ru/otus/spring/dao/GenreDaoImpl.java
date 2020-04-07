package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.ext.GenresBooksResultSetExtractor;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void insert(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource(
                Collections.singletonMap("name", genre.getName()));
        jdbcOperations.update("insert into genres (name) values (:name)", parameterSource, keyHolder);
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.queryForObject(
                "select * from genres where id = :id", params, new GenreMapper());
    }

    @Override
    public Genre getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return jdbcOperations.queryForObject(
                "select * from genres where name = :name", params, new GenreMapper());
    }


    @Override
    public void update(Genre genre) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", genre.getId())
                .addValue("name", genre.getName());
        jdbcOperations.update("update genres set name = :name where id = :id", parameterSource);
    }

    @Override
    public void deleteByName(String name) {
        Genre genre = getByName(name);
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", genre.getId())
                .addValue("name", genre.getName());
        jdbcOperations.update(
                "delete from genres_books where genre_id = :id;" +
                        "delete from genres where name = :name", parameterSource);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update(
                "delete from genres_books where genre_id = :id;" +
                        "delete from genres where id = :id", params);
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.query(
                "select * from genres", new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
