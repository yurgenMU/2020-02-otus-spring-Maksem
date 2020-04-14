package ru.otus.spring.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.ext.BookResultSetExtractor;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.*;

import static java.util.Objects.nonNull;


@Repository
@Transactional
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", book.getName())
                .addValue("author_id", book.getAuthor().getId());
        jdbcOperations.update("insert into books (name, author_id) values (:name, :author_id)", parameterSource, keyHolder);
        long bookId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        MapSqlParameterSource[] batch = getBatch(book, bookId);
        jdbcOperations.batchUpdate(
                "insert into genres_books (genre_id, book_id) values (:genre_id, :book_id)", batch
        );
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Book> books = jdbcOperations.query(
                "select b.id, b.name, a.id as author_id, " +
                        "a.name as author, g.id as genre_id, g.name as genre " +
                        "from books b inner join authors a on b.author_id = a.id " +
                        "inner join genres_books gb on gb.book_id = b.id " +
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
                        "inner join genres_books gb on gb.book_id = b.id " +
                        "inner join genres g on g.id = gb.genre_id where b.name = :name", params,
                new BookResultSetExtractor());
        return nonNull(books) && books.size() != 0 ? books.get(0) : null;
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", genre.getId())
                .addValue("name", genre.getName());
        return jdbcOperations.query("select distinct b.id as book_id, b.name as book, a.id as author_id, a.name as author, " +
                        "g.id as genre_id, g.name as genre " +
                        "from genres g inner join genres_books gb on g.id = gb.genre_id " +
                        "inner join books b on gb.book_id = b.id " +
                        "inner join authors a on a.id = b.author_id where g.id = :id or g.name = :name", mapSqlParameterSource,
                new BookResultSetExtractor());
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("name", author.getName());
        return jdbcOperations.query(
                "select b.id, b.name, a.id as author_id, a.name as author, g.id as genre_id, g.name as genre from books b " +
                        "inner join authors a on b.author_id = a.id " +
                        "inner join genres_books gb on gb.book_id = b.id " +
                        "inner join genres g on g.id = gb.genre_id where a.id = :id or a.name = :name;", parameterSource,
                new BookResultSetExtractor());
    }

    @Override
    public void update(Book book) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("name", book.getName());
        jdbcOperations.update("update books set name = :name where id = :id;" +
                "delete from genres_books where book_id = :id", parameterSource);
        long id = book.getId();
        jdbcOperations.batchUpdate("insert into genres_books values (:genre_id,:book_id)", getBatch(book, id));
    }

    @Override
    public void deleteByName(String name) {
        Book book = getByName(name);
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("name", book.getName());
        jdbcOperations.update("delete from books where name = :name", parameterSource);

    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> bookParams = Collections.singletonMap("id", id);
        jdbcOperations.update(
                "delete from genres_books where book_id = :id;" +
                        " delete from books where id = :id", bookParams);

    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query("select b.id, b.name, a.id as author_id, " +
                        "a.name as author, g.id as genre_id, g.name as genre " +
                        "from books b inner join authors a on b.author_id = a.id " +
                        "inner join genres_books gb on gb.book_id = b.id " +
                        "inner join genres g on g.id = gb.genre_id",
                new BookResultSetExtractor());
    }

    private MapSqlParameterSource[] getBatch(Book book, long bookId) {
        return book.getGenres()
                .stream()
                .map(genre -> new MapSqlParameterSource()
                        .addValue("genre_id", genre.getId())
                        .addValue("book_id", bookId))
                .toArray(MapSqlParameterSource[]::new);
    }

}
