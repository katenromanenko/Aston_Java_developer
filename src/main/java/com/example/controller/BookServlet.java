package com.example.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dto.BookDTO;
import com.example.service.BookService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BookServlet extends HttpServlet {

    private final BookService bookService;

    public BookServlet(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String authorId = req.getParameter("authorId");
        if (id != null) {
            try {
                Long bookId = Long.parseLong(id);
                BookDTO bookDTO = bookService.findById(bookId);
                writeJson(resp, bookDTO);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (authorId != null) {
            try {
                Long authorIdLong = Long.parseLong(authorId);
                List<BookDTO> books = bookService.findByAuthorId(authorIdLong);
                writeJson(resp, books);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            List<BookDTO> books = bookService.findAll();
            writeJson(resp, books);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookDTO bookDTO = new Gson().fromJson(req.getReader(), BookDTO.class);
        BookDTO savedBook = bookService.save(bookDTO);
        writeJson(resp, savedBook);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookDTO bookDTO = new Gson().fromJson(req.getReader(), BookDTO.class);
        bookService.update(bookDTO);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            try {
                Long bookId = Long.parseLong(id);
                bookService.delete(bookId);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void writeJson(HttpServletResponse resp, Object obj) throws IOException {
        Gson gson = new GsonBuilder().create();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        gson.toJson(obj, resp.getWriter());
    }
}