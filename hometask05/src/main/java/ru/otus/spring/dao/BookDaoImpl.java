package ru.otus.spring.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.ext.BookResultSetExtractor;
import ru.otus.spring.domain.Book;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.nonNull;


@Repository
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public BookDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", book.getName())
                .addValue("author_id", book.getAuthor().getId());
        jdbcOperations.update("insert into books (name, author_id) values (:name, :author_id)", parameterSource, keyHolder);
        int bookId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        book.getGenres().forEach(genre -> {
            SqlParameterSource genresBooksParameter = new MapSqlParameterSource()
                    .addValue("genre_id", genre.getId())
                    .addValue("book_id", bookId);
            jdbcOperations.update("insert into genres_books (genre_id, book_id) values (:genre_id, :book_id)", genresBooksParameter);
        });
    }

    @Override
    public Book getById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Book> books = jdbcOperations.query(
                "select b.id, b.name, a.id as author_id, " +
                        "a.name as author, g.id as genre_id, g.name as genre " +
                        "from books b inner join authors a on b.author_id = a.id " +
                        "right join genres_books gb on gb.book_id = b.id " +
                        "inner join genres g on g.id = gb.genre_id where b.id = :id", params,
                new BookResultSetExtractor());
        return nonNull(books) && books.size() != 0 ? books.get(0) : null;
    }

    @Override
    public Book getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        List<Book> books = jdbcOperations.query(
                "select b.id, b.name, a.id as author_id, " +
                        "a.name as author, g.id as genre_id, g.name as genre " +
                        "from books b inner join authors a on b.author_id = a.id " +
                        "right join genres_books gb on gb.book_id = b.id " +
                        "inner join genres g on g.id = gb.genre_id where b.name = :name", params,
                new BookResultSetExtractor());
        return nonNull(books) && books.size() != 0 ? books.get(0) : null;
    }

    @Override
    public void update(Book book) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("name", book.getName());
        jdbcOperations.update("update books set name = :name where id = :id", parameterSource);
        int id = book.getId();
        book.getGenres().forEach(genre -> {
            SqlParameterSource genresBooksParameter = new MapSqlParameterSource()
                    .addValue("genre_id", genre.getId())
                    .addValue("book_id", id);
            jdbcOperations.update("update genres_books set genre_id = :genre_id and book_id = :book_id", genresBooksParameter);
        });
    }

    @Override
    public void deleteByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        Book book = getByName(name);
        jdbcOperations.update(
                "delete from books where name = :name", params);
        Map<String, Object> bookParams = Collections.singletonMap("id", book.getId());
        jdbcOperations.update(
                "delete from genres_books where book_id = :id", bookParams);
    }

    @Override
    public void deleteById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update(
                "delete from books where id = :id", params);
        Map<String, Object> bookParams = Collections.singletonMap("id", id);
        jdbcOperations.update(
                "delete from genres_books where book_id = :id", bookParams);
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query("select b.id, b.name, a.id as author_id, " +
                        "a.name as author, g.id as genre_id, g.name as genre " +
                        "from books b inner join authors a on b.author_id = a.id " +
                        "right join genres_books gb on gb.book_id = b.id " +
                        "inner join genres g on g.id = gb.genre_id",
                new BookResultSetExtractor());
    }

}
