package com.example.dao;

import com.example.model.Book;

import java.util.List;

public interface BookDAO {
    Book save(Book book);
    Book findById(Long id);
    List<Book> findAll();
    void delete(Long id);
    Book update(Book book);
    List<Book> findByAuthorId(Long authorId);
}
