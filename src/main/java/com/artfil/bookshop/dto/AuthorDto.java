package com.artfil.bookshop.dto;

import java.util.List;

public record AuthorDto(Long id, String name, List<BookDto> books) {
}
