package ru.otus.spring.dao.ext;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AuthorBooksResultSetExtractor implements ResultSetExtractor<List<Book>> {


    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Book> books = new HashMap<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            Book book = books.get(id);
            if (book == null) {
                String name = rs.getString("name");
                int genreId = rs.getInt("genre_Id");
                String genre = rs.getString("genre");
                List<Genre> genres = new ArrayList<>();
                genres.add(new Genre(genreId, genre));
                book = new Book(id, name, null, genres);
                books.put(id, book);
            } else {
                int genreId = rs.getInt("genre_Id");
                String genre = rs.getString("genre");
                book.getGenres().add(new Genre(genreId, genre));
            }
        }
        return new ArrayList<>(books.values());
    }
}
