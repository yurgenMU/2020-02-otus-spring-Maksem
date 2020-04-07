package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.ext.AuthorBooksResultSetExtractor;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    @Override
    public void insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource(
                Collections.singletonMap("name", author.getName()));
        jdbcOperations.update("insert into authors (name) values (:name)", parameterSource, keyHolder);
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.queryForObject(
                "select * from authors where id = :id", params, new AuthorMapper());
    }

    @Override
    public Author getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        System.out.println(name);
        return jdbcOperations.queryForObject(
                "select * from authors where name = :name", params, new AuthorMapper());
    }

    @Override
    public void update(Author author) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("name", author.getName());
        jdbcOperations.update("update authors set name = :name where id = :id", parameterSource);
    }

    @Override
    public void deleteByName(String name) {
        Author author = getByName(name);
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("authorId", author.getId())
                .addValue("name", author.getName());
        jdbcOperations.update("delete from authors where name = :name", parameterSource);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update("delete from authors where id = :id", params);
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query(
                "select * from authors", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
