package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.dao.AuthorDAO;
import com.example.dto.AuthorDTO;
import com.example.mapper.AuthorMapper;
import com.example.model.Author;
import org.mapstruct.factory.Mappers;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
        this.authorMapper = Mappers.getMapper(AuthorMapper.class);
    }

    @Override
    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        return authorMapper.toDto(authorDAO.save(author));
    }

    @Override
    public AuthorDTO findById(Long id) {
        Author author = authorDAO.findById(id);
        return authorMapper.toDto(author);
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorDAO.findAll().stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        authorDAO.delete(id);
    }

    @Override
    public AuthorDTO update(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        return authorMapper.toDto(authorDAO.update(author));
    }
}