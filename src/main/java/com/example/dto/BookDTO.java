package com.example.dto;

public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private Long authorId;

    // Конструктор без аргументов
    public BookDTO(long l, String s, String string, long l1) {
    }

    // Конструктор с аргументами title, isbn и authorId
    public BookDTO(String title, String isbn, Long authorId) {
        this.title = title;
        this.isbn = isbn;
        this.authorId = authorId;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
