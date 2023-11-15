package com.artfil.bookshop.controller;

import com.artfil.bookshop.data.Author;
import com.artfil.bookshop.data.Book;
import com.artfil.bookshop.dto.AuthorDto;
import com.artfil.bookshop.dto.BookDto;
import com.artfil.bookshop.service.AuthorService;
import com.artfil.bookshop.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class BookShopController {

    private final AuthorService authorService;
    private final BookService bookService;

    @MutationMapping
    @Transactional
    public Author createAuthor(@Argument String name, @Argument List<BookDto> books) {
        var bookEntities = bookService.checkAndSaveAllNew(books);
        return authorService.save(name, bookEntities);
    }

    @MutationMapping
    @Transactional
    public Book createBook(@Argument String title, @Argument List<AuthorDto> authors) {
        var authorEntities = authorService.checkAndSaveAllNew(authors);
        return bookService.save(title, authorEntities);
    }

    @QueryMapping
    public Set<Author> getAuthorByName(@Argument String name) {
        return authorService.getByName(name);
    }

    @QueryMapping
    public Set<Book> getAllBooks() {
        return bookService.getAll();
    }

    @QueryMapping
    public Set<Book> getBooksByAuthor(@Argument Long authorId) {
        authorService.checkNotFoundWithId(authorId);
        return bookService.getByAuthorId(authorId);
    }
}
