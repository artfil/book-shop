package com.artfil.bookshop.service;

import com.artfil.bookshop.data.Author;
import com.artfil.bookshop.data.Book;
import com.artfil.bookshop.dto.AuthorDto;
import com.artfil.bookshop.error.exception.EntityNotFoundException;
import com.artfil.bookshop.mapper.AuthorMapper;
import com.artfil.bookshop.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository repository;

    public Author save(String name, List<Book> books) {
        var author = AuthorMapper.apply(name, books);
        repository.save(author);
        log.info("Author with id [ {} ] was saved", author.getId());
        return author;
    }

    public Set<Author> getByName(String name) {
        return repository.findByName(name);
    }

    public List<Author> checkAndSaveAllNew(List<AuthorDto> authors) {
        var authorEntities = AuthorMapper.apply(authors);

        authors.stream()
                .map(AuthorDto::id)
                .filter(Objects::nonNull)
                .forEach(this::checkNotFoundWithId);

        var newEntities = authorEntities.stream()
                .filter(a -> Objects.isNull(a.getId()))
                .toList();

        List<Long> savedIds = repository.saveAll(newEntities)
                .stream()
                .map(Author::getId)
                .toList();

        log.info("[ {} ] authors with ids {} was saved", savedIds.size(), savedIds);

        return authorEntities;
    }

    public void checkNotFoundWithId(Long id) {
        if (repository.findById(id).isEmpty())
            throw new EntityNotFoundException(String.format("Author with id [ %d ] does not exist", id));
    }
}
