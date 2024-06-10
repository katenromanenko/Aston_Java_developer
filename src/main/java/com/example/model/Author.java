package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private Long id;
    private String name;
    private List<Book> books = new ArrayList<>();

    // Конструктор без аргументов
    public Author() {
    }

    // Конструктор с именем
    public Author(String name) {
        this.name = name;
    }

    // Конструктор с id и name
    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // Метод для добавления книги в список книг автора
    public void addBook(Book book) {
        books.add(book);
    }
}
