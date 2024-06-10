package com.example.service;

import com.example.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO save(AuthorDTO authorDTO);
    AuthorDTO findById(Long id);
    List<AuthorDTO> findAll();
    void delete(Long id);
    AuthorDTO update(AuthorDTO authorDTO);
}
