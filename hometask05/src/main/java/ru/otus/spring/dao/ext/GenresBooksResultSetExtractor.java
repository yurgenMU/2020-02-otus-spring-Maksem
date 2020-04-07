package ru.otus.spring.dao.ext;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GenresBooksResultSetExtractor implements ResultSetExtractor<List<Book>> {
    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Book> books = new HashMap<>();
        while (rs.next()) {
            long id = rs.getLong("book_id");
            String name = rs.getString("book");
            long authorId = rs.getLong("author_id");
            String author = rs.getString("author");
            Book book = new Book(id, name, new Author(authorId, author), Collections.emptyList());
            books.put(id, book);
        }
        return new ArrayList<>(books.values());
    }
}
