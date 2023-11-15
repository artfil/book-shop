package com.artfil.bookshop.service;

import com.artfil.bookshop.data.Author;
import com.artfil.bookshop.data.Book;
import com.artfil.bookshop.dto.BookDto;
import com.artfil.bookshop.error.exception.EntityNotFoundException;
import com.artfil.bookshop.mapper.BookMapper;
import com.artfil.bookshop.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository repository;

    public Book save(String title, List<Author> authors) {
        var book = BookMapper.apply(title, authors);
        repository.save(book);
        log.info("Book with id [ {} ] was saved", book.getId());
        return book;
    }

    public Set<Book> getAll() {
        return new HashSet<>(repository.findAll());
    }

    public Set<Book> getByAuthorId(Long authorId) {
        return repository.findAllByAuthorId(authorId);
    }

    public List<Book> checkAndSaveAllNew(List<BookDto> books) {
        var bookEntities = BookMapper.apply(books);

        bookEntities.stream()
                .map(Book::getId)
                .filter(Objects::nonNull)
                .forEach(this::checkNotFoundWithId);

        var newEntities = bookEntities.stream()
                .filter(a -> Objects.isNull(a.getId()))
                .toList();

        List<Long> savedIds = repository.saveAll(newEntities)
                .stream()
                .map(Book::getId)
                .toList();

        log.info("[ {} ] books with ids {} was saved", savedIds.size(), savedIds);

        return bookEntities;
    }

    private void checkNotFoundWithId(Long id) {
        if (repository.findById(id).isEmpty())
            throw new EntityNotFoundException(String.format("Book with id [ %d ] does not exist", id));
    }
}
