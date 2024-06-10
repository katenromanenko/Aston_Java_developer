package com.example.dto;

public class AuthorDTO {
    private Long id;
    private String name;

    // Конструктор без аргументов
    public AuthorDTO() {
    }

    // Конструктор с id и name
    public AuthorDTO(Long id, String name) {
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
}
