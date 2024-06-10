package com.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.dao.AuthorDAO;
import com.example.dto.AuthorDTO;
import com.example.mapper.AuthorMapper;
import com.example.model.Author;

public class AuthorServiceTest {

    private AuthorService authorService;
    private AuthorDAO authorDAO;
    private AuthorMapper authorMapper;

    @BeforeEach
    void setUp() {
        authorDAO = Mockito.mock(AuthorDAO.class);
        authorMapper = Mockito.mock(AuthorMapper.class);
        authorService = new AuthorServiceImpl(authorDAO);
    }

    @Test
    void testSave() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Nikolai Gogol");
        Author author = new Author();
        author.setId(1L);
        author.setName("Nikolai Gogol");
        when(authorDAO.save(any(Author.class))).thenReturn(author);
        when(authorMapper.toEntity(any(AuthorDTO.class))).thenReturn(author);
        when(authorMapper.toDto(any(Author.class))).thenReturn(authorDTO);

        AuthorDTO savedAuthorDTO = authorService.save(authorDTO);
        assertEquals(1L, savedAuthorDTO.getId());
        assertEquals("Nikolai Gogol", savedAuthorDTO.getName()); //  Проверка с новым именем

        verify(authorDAO, times(1)).save(any(Author.class));
        verify(authorMapper, times(1)).toEntity(any(AuthorDTO.class));
        verify(authorMapper, times(1)).toDto(any(Author.class));
    }

    @Test
    void testFindById() {
        AuthorDTO authorDTO = new AuthorDTO(1L, "Nikolai Gogol");
        Author author = new Author(1L, "Nikolai Gogol");
        when(authorDAO.findById(1L)).thenReturn(author);
        when(authorMapper.toDto(any(Author.class))).thenReturn(authorDTO);

        AuthorDTO foundAuthorDTO = authorService.findById(1L);
        assertEquals(1L, foundAuthorDTO.getId());
        assertEquals("Nikolai Gogol", foundAuthorDTO.getName());

        verify(authorDAO, times(1)).findById(1L);
        verify(authorMapper, times(1)).toDto(any(Author.class));
    }

    @Test
    void testFindAll() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1L, "Nikolai Gogol"));
        authors.add(new Author(2L, "Nikolai Nabokov"));

        List<AuthorDTO> authorDTOs = new ArrayList<>();
        authorDTOs.add(new AuthorDTO(1L, "Nikolai Gogol"));
        authorDTOs.add(new AuthorDTO(2L, "Nikolai Nabokov"));

        when(authorDAO.findAll()).thenReturn(authors);
        when(authorMapper.toDto(any(Author.class))).thenReturn(authorDTOs.get(0)); //  Устанавливаем мок для каждого AuthorDTO
        when(authorMapper.toDto(any(Author.class))).thenReturn(authorDTOs.get(1)); //  Устанавливаем мок для каждого AuthorDTO

        List<AuthorDTO> foundAuthorDTOs = authorService.findAll();
        assertEquals(2, foundAuthorDTOs.size());
        assertEquals(authorDTOs.get(0), foundAuthorDTOs.get(0));
        assertEquals(authorDTOs.get(1), foundAuthorDTOs.get(1));

        verify(authorDAO, times(1)).findAll();
        verify(authorMapper, times(2)).toDto(any(Author.class));
    }

    @Test
    void testDelete() {
        doNothing().when(authorDAO).delete(1L);

        authorService.delete(1L);

        verify(authorDAO, times(1)).delete(1L);
    }

    @Test
    void testUpdate() {
        AuthorDTO authorDTO = new AuthorDTO(1L, "Nikolai Gogol");
        Author author = new Author(1L, "Nikolai Gogol");
        when(authorDAO.update(any(Author.class))).thenReturn(author);
        when(authorMapper.toEntity(any(AuthorDTO.class))).thenReturn(author);
        when(authorMapper.toDto(any(Author.class))).thenReturn(authorDTO);

        AuthorDTO updatedAuthorDTO = authorService.update(authorDTO);
        assertEquals(1L, updatedAuthorDTO.getId());
        assertEquals("Nikolai Gogol", updatedAuthorDTO.getName());

        verify(authorDAO, times(1)).update(any(Author.class));
        verify(authorMapper, times(1)).toEntity(any(AuthorDTO.class));
        verify(authorMapper, times(1)).toDto(any(Author.class));
    }
}
