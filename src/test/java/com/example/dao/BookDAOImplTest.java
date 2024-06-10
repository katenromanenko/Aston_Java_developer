package com.example.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.model.Author;
import com.example.model.Book;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Testcontainers
public class BookDAOImplTest {

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("book_app");

    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(mysqlContainer.getJdbcUrl());
        hikariConfig.setUsername(mysqlContainer.getUsername());
        hikariConfig.setPassword(mysqlContainer.getPassword());
        dataSource = new HikariDataSource(hikariConfig);
        bookDAO = new BookDAOImpl(dataSource, new AuthorDAOImpl(dataSource));
        authorDAO = new AuthorDAOImpl(dataSource);
    }

    @Test
    void testSave() {
        Author author = new Author();
        author.setName("Nikolai Gogol");
        author = authorDAO.save(author);

        Book book = new Book();
        book.setTitle("Evenings on a Farm Near Dikanka");
        book.setIsbn("978-0-345-39180-3");
        book.setAuthor(author);
        Book savedBook = bookDAO.save(book);
        assertNotNull(savedBook.getId());
        assertEquals("Evenings on a Farm Near Dikanka", savedBook.getTitle());
        assertEquals("978-0-345-39180-3", savedBook.getIsbn());
        assertEquals(author.getId(), savedBook.getAuthor().getId());
    }

    @Test
    void testFindById() {
        Author author = new Author();
        author.setName("Nikolai Gogol");
        author = authorDAO.save(author);

        Book book = new Book();
        book.setTitle("Evenings on a Farm Near Dikanka");
        book.setIsbn("978-0-345-39180-3");
        book.setAuthor(author);
        book = bookDAO.save(book);

        Book foundBook = bookDAO.findById(book.getId());
        assertNotNull(foundBook);
        assertEquals(book.getId(), foundBook.getId());
        assertEquals("Evenings on a Farm Near Dikanka", foundBook.getTitle());
        assertEquals("978-0-345-39180-3", foundBook.getIsbn());
        assertEquals(author.getId(), foundBook.getAuthor().getId());
    }

    @Test
    void testFindAll() {
        Author author = new Author();
        author.setName("Nikolai Gogol");
        author = authorDAO.save(author);

        Book book1 = new Book();
        book1.setTitle("Evenings on a Farm Near Dikanka");
        book1.setIsbn("978-0-345-39180-3");
        book1.setAuthor(author);
        bookDAO.save(book1);

        Book book2 = new Book();
        book2.setTitle("Dead Souls");
        book2.setIsbn("978-0-345-39181-0");
        book2.setAuthor(author);
        bookDAO.save(book2);

        List<Book> books = bookDAO.findAll();
        assertEquals(2, books.size());
        assertTrue(books.contains(book1));
        assertTrue(books.contains(book2));
    }

    @Test
    void testDelete() {
        Author author = new Author();
        author.setName("Nikolai Gogol");
        author = authorDAO.save(author);

        Book book = new Book();
        book.setTitle("Evenings on a Farm Near Dikanka");
        book.setIsbn("978-0-345-39180-3");
        book.setAuthor(author);
        book = bookDAO.save(book);

        bookDAO.delete(book.getId());

        Book deletedBook = bookDAO.findById(book.getId());
        assertNull(deletedBook);
    }

    @Test
    void testUpdate() {
        Author author = new Author();
        author.setName("Nikolai Gogol");
        author = authorDAO.save(author);

        Book book = new Book();
        book.setTitle("Evenings on a Farm Near Dikanka");
        book.setIsbn("978-0-345-39180-3");
        book.setAuthor(author);
        book = bookDAO.save(book);

        book.setTitle("Dead Souls");
        book.setIsbn("978-0-345-39181-0");
        bookDAO.update(book);

        Book updatedBook = bookDAO.findById(book.getId());
        assertEquals("Dead Souls", updatedBook.getTitle());
        assertEquals("978-0-345-39181-0", updatedBook.getIsbn());
        assertEquals(author.getId(), updatedBook.getAuthor().getId());
    }

    @Test
    void testFindByAuthorId() {
        Author author = new Author();
        author.setName("Nikolai Gogol");
        author = authorDAO.save(author);

        Book book1 = new Book();
        book1.setTitle("Evenings on a Farm Near Dikanka");
        book1.setIsbn("978-0-345-39180-3");
        book1.setAuthor(author);
        bookDAO.save(book1);

        Book book2 = new Book();
        book2.setTitle("Dead Souls");
        book2.setIsbn("978-0-345-39181-0");
        book2.setAuthor(author);
        bookDAO.save(book2);

        List<Book> booksByAuthor = bookDAO.findByAuthorId(author.getId());
        assertEquals(2, booksByAuthor.size());
        assertTrue(booksByAuthor.contains(book1));
        assertTrue(booksByAuthor.contains(book2));
    }
}