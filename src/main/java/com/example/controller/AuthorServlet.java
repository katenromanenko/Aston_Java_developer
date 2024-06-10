package com.example.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dto.AuthorDTO;
import com.example.service.AuthorService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AuthorServlet extends HttpServlet {

    private final AuthorService authorService;

    public AuthorServlet(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            try {
                Long authorId = Long.parseLong(id);
                AuthorDTO authorDTO = authorService.findById(authorId);
                writeJson(resp, authorDTO);
            } catch (NumberFormatException e) {
                sendError(resp, "Неверный формат ID");
            }
        } else {
            List<AuthorDTO> authors = authorService.findAll();
            writeJson(resp, authors);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthorDTO authorDTO = new Gson().fromJson(req.getReader(), AuthorDTO.class);
        AuthorDTO savedAuthor = authorService.save(authorDTO);
        writeJson(resp, savedAuthor);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthorDTO authorDTO = new Gson().fromJson(req.getReader(), AuthorDTO.class);
        authorService.update(authorDTO);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            try {
                Long authorId = Long.parseLong(id);
                authorService.delete(authorId);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                sendError(resp, "Неверный формат ID");
            }
        } else {
            sendError(resp, "ID не указан");
        }
    }

    private void writeJson(HttpServletResponse resp, Object obj) throws IOException {
        Gson gson = new GsonBuilder().create();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        gson.toJson(obj, resp.getWriter());
    }

    private void sendError(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().create();
        gson.toJson(Map.of("error", message), resp.getWriter());
    }
}