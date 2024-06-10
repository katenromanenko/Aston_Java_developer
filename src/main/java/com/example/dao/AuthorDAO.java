package com.example.dao;

import com.example.model.Author;

import java.util.List;

public interface AuthorDAO {
    Author save(Author author);
    Author findById(Long id);
    List<Author> findAll();
    void delete(Long id);
    Author update(Author author);
}
