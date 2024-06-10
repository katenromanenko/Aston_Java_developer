package com.example.dao;

import com.example.model.Author;
import com.example.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class BookDAOImpl implements BookDAO {

    private final DataSource dataSource;
    private final AuthorDAO authorDAO;

    public BookDAOImpl(DataSource dataSource, AuthorDAO authorDAO) {
        this.dataSource = dataSource;
        this.authorDAO = authorDAO;
    }

    @Override
    public Book save(Book book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Book (title, isbn, author_id) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getIsbn());
            statement.setLong(3, book.getAuthor().getId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                }
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Book WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Author author = authorDAO.findById(resultSet.getLong("author_id"));
                    return new Book(
                            resultSet.getLong("id"),
                            resultSet.getString("title"),
                            resultSet.getString("isbn"),
                            author
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Book")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Author author = authorDAO.findById(resultSet.getLong("author_id"));
                    books.add(new Book(
                            resultSet.getLong("id"),
                            resultSet.getString("title"),
                            resultSet.getString("isbn"),
                            author
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Book WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Book SET title = ?, isbn = ?, author_id = ? WHERE id = ?")) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getIsbn());
            statement.setLong(3, book.getAuthor().getId());
            statement.setLong(4, book.getId());
            statement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findByAuthorId(Long authorId) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Book WHERE author_id = ?")) {
            statement.setLong(1, authorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Author author = authorDAO.findById(resultSet.getLong("author_id"));
                    books.add(new Book(
                            resultSet.getLong("id"),
                            resultSet.getString("title"),
                            resultSet.getString("isbn"),
                            author
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }
}