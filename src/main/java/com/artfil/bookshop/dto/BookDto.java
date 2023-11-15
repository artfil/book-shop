package com.artfil.bookshop.dto;

import java.util.List;

public record BookDto(Long id, String title, List<AuthorDto> authors) {
}
