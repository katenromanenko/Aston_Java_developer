package com.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.dao.AuthorDAO;
import com.example.dao.BookDAO;
import com.example.dto.BookDTO;
import com.example.mapper.BookMapper;
import com.example.model.Author;
import com.example.model.Book;

public class BookServiceTest {

    private BookService bookService;
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookDAO = Mockito.mock(BookDAO.class);
        authorDAO = Mockito.mock(AuthorDAO.class);
        bookMapper = Mockito.mock(BookMapper.class);
        bookService = new BookServiceImpl(bookDAO, authorDAO);
    }

    @Test
    void testSave() {
        BookDTO bookDTO = new BookDTO(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", 1L);
        bookDTO.setTitle("The Hitchhiker's Guide to the Galaxy");
        bookDTO.setIsbn("978-0-345-39180-3");
        bookDTO.setAuthorId(1L);
        Author author = new Author();
        author.setId(1L);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The Hitchhiker's Guide to the Galaxy");
        book.setIsbn("978-0-345-39180-3");
        book.setAuthor(author);
        when(authorDAO.findById(1L)).thenReturn(author);
        when(bookDAO.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toEntity(any(BookDTO.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTO);

        BookDTO savedBookDTO = bookService.save(bookDTO);
        assertEquals(1L, savedBookDTO.getId());
        assertEquals("The Hitchhiker's Guide to the Galaxy", savedBookDTO.getTitle());
        assertEquals("978-0-345-39180-3", savedBookDTO.getIsbn());
        assertEquals(1L, savedBookDTO.getAuthorId());

        verify(authorDAO, times(1)).findById(1L);
        verify(bookDAO, times(1)).save(any(Book.class));
        verify(bookMapper, times(1)).toEntity(any(BookDTO.class));
        verify(bookMapper, times(1)).toDto(any(Book.class));
    }

    @Test
    void testFindById() {
        BookDTO bookDTO = new BookDTO(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", 1L);
        Author author = new Author(1L, "Nikolai Gogol");
        Book book = new Book(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", author);

        when(bookDAO.findById(1L)).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTO);

        BookDTO foundBookDTO = bookService.findById(1L);
        assertEquals(1L, foundBookDTO.getId());
        assertEquals("Evenings on a Farm Near Dikanka", foundBookDTO.getTitle());
        assertEquals("978-0-345-39180-3", foundBookDTO.getIsbn());
        assertEquals(1L, foundBookDTO.getAuthorId());

        verify(bookDAO, times(1)).findById(1L);
        verify(bookMapper, times(1)).toDto(any(Book.class));
    }

    @Test
    void testFindAll() {
        Author author = new Author(1L, "Nikolai Nabokov");
        Book book1 = new Book(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", author);
        Book book2 = new Book(2L, "Dead Souls", "978-0-345-39181-0", author);
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        List<BookDTO> bookDTOs = new ArrayList<>();
        bookDTOs.add(new BookDTO(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", 1L));
        bookDTOs.add(new BookDTO(2L, "Dead Souls", "978-0-345-39181-0", 1L));

        when(bookDAO.findAll()).thenReturn(books);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTOs.get(0)); //  Устанавливаем мок для каждого BookDTO
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTOs.get(1)); //  Устанавливаем мок для каждого BookDTO

        List<BookDTO> foundBookDTOs = bookService.findAll();
        assertEquals(2, foundBookDTOs.size());
        assertEquals(bookDTOs.get(0), foundBookDTOs.get(0));
        assertEquals(bookDTOs.get(1), foundBookDTOs.get(1));

        verify(bookDAO, times(1)).findAll();
        verify(bookMapper, times(2)).toDto(any(Book.class));
    }

    @Test
    void testDelete() {
        doNothing().when(bookDAO).delete(1L);

        bookService.delete(1L);

        verify(bookDAO, times(1)).delete(1L);
    }

    @Test
    void testUpdate() {
        BookDTO bookDTO = new BookDTO(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", 1L);
        Author author = new Author(1L, "Nikolai Nabokov");
        Book book = new Book(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", author);

        when(authorDAO.findById(1L)).thenReturn(author);
        when(bookDAO.update(any(Book.class))).thenReturn(book);
        when(bookMapper.toEntity(any(BookDTO.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTO);

        BookDTO updatedBookDTO = bookService.update(bookDTO);
        assertEquals(1L, updatedBookDTO.getId());
        assertEquals("Evenings on a Farm Near Dikanka", updatedBookDTO.getTitle());
        assertEquals("978-0-345-39180-3", updatedBookDTO.getIsbn());
        assertEquals(1L, updatedBookDTO.getAuthorId());

        verify(authorDAO, times(1)).findById(1L);
        verify(bookDAO, times(1)).update(any(Book.class));
        verify(bookMapper, times(1)).toEntity(any(BookDTO.class));
        verify(bookMapper, times(1)).toDto(any(Book.class));
    }

    @Test
    void testFindByAuthorId() {
        Author author = new Author(1L, "Nikolai Nabokov");
        Book book1 = new Book(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", author);
        Book book2 = new Book(2L, "Dead Souls", "978-0-345-39181-0", author);
        List<Book> booksByAuthor = new ArrayList<>();
        booksByAuthor.add(book1);
        booksByAuthor.add(book2);

        List<BookDTO> bookDTOs = new ArrayList<>();
        bookDTOs.add(new BookDTO(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", 1L));
        bookDTOs.add(new BookDTO(2L, "Dead Souls", "978-0-345-39181-0", 1L));

        when(bookDAO.findByAuthorId(1L)).thenReturn(booksByAuthor);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTOs.get(0)); //  Устанавливаем мок для каждого BookDTO
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTOs.get(1)); //  Устанавливаем мок для каждого BookDTO

        List<BookDTO> foundBookDTOs = bookService.findByAuthorId(1L);
        assertEquals(2, foundBookDTOs.size());
        assertEquals(bookDTOs.get(0), foundBookDTOs.get(0));
        assertEquals(bookDTOs.get(1), foundBookDTOs.get(1));

        verify(bookDAO, times(1)).findByAuthorId(1L);
        verify(bookMapper, times(2)).toDto(any(Book.class));
    }
}