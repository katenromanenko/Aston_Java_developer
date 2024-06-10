package com.example.service;

import com.example.dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO save(BookDTO bookDTO);
    BookDTO findById(Long id);
    List<BookDTO> findAll();
    void delete(Long id);
    BookDTO update(BookDTO bookDTO);
    List<BookDTO> findByAuthorId(Long authorId);
}
