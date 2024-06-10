package com.example.controller;

import com.example.dto.AuthorDTO;
import com.example.service.AuthorService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

public class AuthorServletTest {

    private AuthorServlet authorServlet;

    @Mock
    private AuthorService authorService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    // Инициализация мока перед каждым тестом
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authorServlet = new AuthorServlet(authorService);
    }

    // Тестирование метода doGet с передачей ID
    @Test
    public void testDoGetWithId() throws Exception {
        Long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(authorId);
        authorDTO.setName("Author Name");

        when(request.getParameter("id")).thenReturn(String.valueOf(authorId));
        when(authorService.findById(authorId)).thenReturn(authorDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Вызов метода doGet
        authorServlet.doGet(request, response);

        // Проверка результатов
        verify(authorService).findById(authorId);
        assertEquals(new Gson().toJson(authorDTO), stringWriter.toString());
    }

    // Тестирование метода doGet без передачи ID (получение всех авторов)
    @Test
    public void testDoGetWithoutId() throws Exception {
        List<AuthorDTO> authors = Collections.singletonList(new AuthorDTO());
        when(request.getParameter("id")).thenReturn(null);
        when(authorService.findAll()).thenReturn(authors);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Вызов метода doGet
        authorServlet.doGet(request, response);

        // Проверка результатов
        verify(authorService).findAll();
        assertEquals(new Gson().toJson(authors), stringWriter.toString());
    }

    // Тестирование метода doPost
    @Test
    public void testDoPost() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Author Name");
        AuthorDTO savedAuthorDTO = new AuthorDTO();
        savedAuthorDTO.setId(1L);
        savedAuthorDTO.setName("Author Name");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(authorDTO))));
        when(authorService.save(any(AuthorDTO.class))).thenReturn(savedAuthorDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Вызов метода doPost
        authorServlet.doPost(request, response);

        // Проверка результатов
        verify(authorService).save(any(AuthorDTO.class));
        assertEquals(new Gson().toJson(savedAuthorDTO), stringWriter.toString());
        assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());
    }

    // Тестирование метода doPut
    @Test
    public void testDoPut() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("Author Name");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(authorDTO))));

        // Вызов метода doPut
        authorServlet.doPut(request, response);

        // Проверка результатов
        verify(authorService).update(any(AuthorDTO.class));
        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
    }

    // Тестирование метода doDelete
    @Test
    public void testDoDelete() throws Exception {
        Long authorId = 1L;
        when(request.getParameter("id")).thenReturn(String.valueOf(authorId));

        // Вызов метода doDelete
        authorServlet.doDelete(request, response);

        // Проверка результатов
        verify(authorService).delete(authorId);
        assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
    }
}

