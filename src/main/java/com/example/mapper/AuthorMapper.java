package com.example.mapper;

import com.example.dto.AuthorDTO;
import com.example.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(AuthorDTO authorDTO);
    AuthorDTO toDto(Author author);
}
