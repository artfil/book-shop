package com.artfil.bookshop.mapper;

import com.artfil.bookshop.data.Author;
import com.artfil.bookshop.data.Book;
import com.artfil.bookshop.dto.BookDto;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BookMapper {

    public Book apply(String title, List<Author> authors) {
        return Book.builder()
                .title(title)
                .authors(new HashSet<>(authors))
                .build();
    }

    public List<Book> apply(List<BookDto> booksDto) {
        return booksDto.stream()
                .map(BookMapper::apply)
                .collect(Collectors.toList());
    }

    private Book apply(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.id())
                .title(bookDto.title())
                .build();
    }
}
