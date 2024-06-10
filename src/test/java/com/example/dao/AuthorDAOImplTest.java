package com.example.dao;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.model.Author;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Testcontainers
public class AuthorDAOImplTest {

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("book_app");

    private AuthorDAO authorDAO;
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(mysqlContainer.getJdbcUrl());
        hikariConfig.setUsername(mysqlContainer.getUsername());
        hikariConfig.setPassword(mysqlContainer.getPassword());
        dataSource = new HikariDataSource(hikariConfig);
        authorDAO = new AuthorDAOImpl(dataSource);
    }

    @Test
    void testSave() {
        Author author = new Author();
        author.setName("Nikolai Gogol");
        Author savedAuthor = authorDAO.save(author);
        assertNotNull(savedAuthor.getId());
        assertEquals("Nikolai Gogol", savedAuthor.getName());
    }

    @Test
    void testFindById() {
        Author author = new Author();
        author.setName("Nikolai Gogol");
        author = authorDAO.save(author);
        Author foundAuthor = authorDAO.findById(author.getId());
        assertNotNull(foundAuthor);
        assertEquals(author.getId(), foundAuthor.getId());
        assertEquals("Nikolai Gogol", foundAuthor.getName());
    }

    @Test
    void testFindAll() {
        Author author1 = new Author();
        author1.setName("Vladimir Nabokov");
        authorDAO.save(author1);
        Author author2 = new Author();
        author2.setName("Sergey Yesenin");
        authorDAO.save(author2);

        List<Author> authors = authorDAO.findAll();
        assertEquals(2, authors.size());
        assertTrue(authors.contains(author1));
        assertTrue(authors.contains(author2));
    }

    @Test
    void testDelete() {
        Author author = new Author();
        author.setName("Fyodor Dostoevsky");
        author = authorDAO.save(author);
        authorDAO.delete(author.getId());

        Author deletedAuthor = authorDAO.findById(author.getId());
        assertNull(deletedAuthor);
    }

    @Test
    void testUpdate() {
        Author author = new Author();
        author.setName("Leo Tolstoy");
        author = authorDAO.save(author);
        author.setName("Joseph Brodsky");

        authorDAO.update(author);
        Author updatedAuthor = authorDAO.findById(author.getId());
        assertEquals("Joseph Brodsky", updatedAuthor.getName());
    }
}
