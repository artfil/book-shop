package com.artfil.bookshop.mapper;

import com.artfil.bookshop.data.Author;
import com.artfil.bookshop.data.Book;
import com.artfil.bookshop.dto.AuthorDto;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class AuthorMapper {

    public Author apply(String name, List<Book> books) {
        return Author.builder()
                .name(name)
                .books(new HashSet<>(books))
                .build();
    }

    public List<Author> apply(List<AuthorDto> authorsDto) {
        return authorsDto.stream()
                .map(AuthorMapper::apply)
                .collect(Collectors.toList());
    }

    private Author apply(AuthorDto authorDto) {
        return Author.builder()
                .id(authorDto.id())
                .name(authorDto.name())
                .build();
    }
}
