package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.dao.AuthorDAO;
import com.example.dao.BookDAO;
import com.example.dto.BookDTO;
import com.example.mapper.BookMapper;
import com.example.model.Book;
import org.mapstruct.factory.Mappers;

public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookDAO bookDAO, AuthorDAO authorDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.bookMapper = Mappers.getMapper(BookMapper.class);
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        book.setAuthor(authorDAO.findById(bookDTO.getAuthorId()));
        return bookMapper.toDto(bookDAO.save(book));
    }

    @Override
    public BookDTO findById(Long id) {
        Book book = bookDAO.findById(id);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDTO> findAll() {
        return bookDAO.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        bookDAO.delete(id);
    }

    @Override
    public BookDTO update(BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        book.setAuthor(authorDAO.findById(bookDTO.getAuthorId()));
        return bookMapper.toDto(bookDAO.update(book));
    }

    @Override
    public List<BookDTO> findByAuthorId(Long authorId) {
        return bookDAO.findByAuthorId(authorId).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}
