package com.example.dao;

import com.example.model.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class AuthorDAOImpl implements AuthorDAO {

    private final DataSource dataSource;

    public AuthorDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Author save(Author author) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Author (name) VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author.getName());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    author.setId(generatedKeys.getLong(1));
                }
            }
            return author;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Author findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Author WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Author(
                            resultSet.getLong("id"),
                            resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Author")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    authors.add(new Author(
                            resultSet.getLong("id"),
                            resultSet.getString("name")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Author WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Author update(Author author) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Author SET name = ? WHERE id = ?")) {
            statement.setString(1, author.getName());
            statement.setLong(2, author.getId());
            statement.executeUpdate();
            return author;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}