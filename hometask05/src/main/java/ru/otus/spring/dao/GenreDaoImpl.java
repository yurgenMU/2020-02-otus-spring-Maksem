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
    public Genre getById(int id) {
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
    public List<Book> getBooksByGenre(Genre genre) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", genre.getId())
                .addValue("name", genre.getName());
        return jdbcOperations.query("select distinct b.id as book_id, b.name as book, a.id as author_id, a.name as author " +
                        "from genres g inner join genres_books gb on g.id = gb.genre_id " +
                        "inner join books b on gb.book_id = b.id " +
                        "inner join authors a on a.id = b.author_id where g.id = :id or g.name = :name", mapSqlParameterSource,
                new GenresBooksResultSetExtractor());
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
        Map<String, Object> params = Collections.singletonMap("name", name);
        jdbcOperations.update(
                "delete from genres where name = :name", params);
    }

    @Override
    public void deleteById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update(
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
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
