package com.example;

import java.io.IOException;
import java.io.Serial;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.controller.AuthorServlet;
import com.example.controller.BookServlet;
import com.example.dao.AuthorDAO;
import com.example.dao.AuthorDAOImpl;
import com.example.dao.BookDAO;
import com.example.dao.BookDAOImpl;
import com.example.dao.ConnectionDB;
import com.example.dto.AuthorDTO;
import com.example.dto.BookDTO;
import com.example.service.AuthorService;
import com.example.service.AuthorServiceImpl;
import com.example.service.BookService;
import com.example.service.BookServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.Servlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private static HikariDataSource dataSource;
    private static AuthorService authorService;
    private static BookService bookService;
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Override
    public void init() throws ServletException {
        super.init();

        //  Инициализируем соединение с помощью ConnectionDB
        dataSource = ConnectionDB.getHikariDataSource();  //  Используем ConnectionDB для получения dataSource

        //  Инициализируем DAO и сервисы
        AuthorDAO authorDAO = new AuthorDAOImpl(dataSource);
        BookDAO bookDAO = new BookDAOImpl(dataSource, authorDAO);  //  Передаем AuthorDAO в BookDAOImpl
        authorService = new AuthorServiceImpl(authorDAO);
        bookService = new BookServiceImpl(bookDAO, authorDAO);

        //  Создаем сервлет
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder((Servlet) new AuthorServlet(authorService)), "/authors/*");
        contextHandler.addServlet(new ServletHolder((Servlet) new BookServlet(bookService)), "/books/*");

        //  Запускаем сервер
        Server server = new Server(8080);
        server.setHandler(contextHandler);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            LOGGER.error("Ошибка при запуске сервера", e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  Получаем параметр id из запроса (если он есть)
        String id = request.getParameter("id");

        //  Обработка запросов
        if (id != null) {
            //  Обработка запроса на получение объекта по id
            try {
                Long bookId = Long.parseLong(id);
                BookDTO bookDTO = bookService.findById(bookId);
                writeJson(response, bookDTO);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            //  Обработка запроса на получение списка объектов
            List<BookDTO> books = bookService.findAll();
            writeJson(response, books);
        }
    }

    private void writeJson(HttpServletResponse response, Object obj) throws IOException {
        Gson gson = new GsonBuilder().create();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        gson.toJson(obj, response.getWriter());
    }
}