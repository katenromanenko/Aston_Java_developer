package com.example.controller;

import com.example.dto.BookDTO;
import com.example.service.BookService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServletTest {

    private BookServlet bookServlet;

    @Mock
    private BookService bookService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() {
        bookServlet = new BookServlet(bookService);
    }

    @Test
    public void testDoGetWithId() throws Exception {
        Long bookId = 1L;
        BookDTO bookDTO = new BookDTO(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", 1L);
        bookDTO.setId(bookId);
        bookDTO.setTitle("Book Title");

        when(request.getParameter("id")).thenReturn(String.valueOf(bookId));
        when(bookService.findById(bookId)).thenReturn(bookDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        bookServlet.doGet(request, response);

        verify(bookService).findById(bookId);
        assertEquals(new Gson().toJson(bookDTO), stringWriter.toString());
    }

    @Test
    public void testDoGetWithAuthorId() throws Exception {
        Long authorId = 1L;
        List<BookDTO> books = Collections.singletonList(new BookDTO(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", 1L));

        when(request.getParameter("authorId")).thenReturn(String.valueOf(authorId));
        when(bookService.findByAuthorId(authorId)).thenReturn(books);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        bookServlet.doGet(request, response);

        verify(bookService).findByAuthorId(authorId);
        assertEquals(new Gson().toJson(books), stringWriter.toString());
    }

    @Test
    public void testDoGetWithoutIdOrAuthorId() throws Exception {
        List<BookDTO> books = Collections.singletonList(new BookDTO(1L, "Evenings on a Farm Near Dikanka", "978-0-345-39180-3", 1L));
        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("authorId")).thenReturn(null);
        when(bookService.findAll()).thenReturn(books);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        bookServlet.doGet(request, response);

        verify(bookService).findAll();
        assertEquals(new Gson().toJson(books), stringWriter.toString());
    }

    @Test
    public void testDoPost() throws Exception {
        BookDTO bookDTO = new BookDTO("Book Title", "1234567890", 1L);
        BookDTO savedBookDTO = new BookDTO("Book Title", "1234567890", 1L);
        savedBookDTO.setId(1L);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(bookDTO))));
        when(bookService.save(any(BookDTO.class))).thenReturn(savedBookDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        bookServlet.doPost(request, response);

        verify(bookService).save(any(BookDTO.class));
        assertEquals(new Gson().toJson(savedBookDTO), stringWriter.toString());
    }

    @Test
    public void testDoPut() throws Exception {
        BookDTO bookDTO = new BookDTO("Book Title", "1234567890", 1L);
        bookDTO.setId(1L);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(bookDTO))));

        bookServlet.doPut(request, response);

        verify(bookService).update(any(BookDTO.class));
        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
    }

    @Test
    public void testDoDelete() throws Exception {
        Long bookId = 1L;
        when(request.getParameter("id")).thenReturn(String.valueOf(bookId));

        bookServlet.doDelete(request, response);

        verify(bookService).delete(bookId);
        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
    }
}





