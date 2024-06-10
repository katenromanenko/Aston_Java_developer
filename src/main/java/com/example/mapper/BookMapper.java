package com.example.mapper;

import com.example.dto.BookDTO;
import com.example.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookDTO bookDTO);
    BookDTO toDto(Book book);
}
