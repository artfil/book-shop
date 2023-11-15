package com.artfil.bookshop.dao;

import com.artfil.bookshop.BookShopApplication;
import com.artfil.bookshop.common.BookShopPostgresqlContainer;
import com.artfil.bookshop.data.Author;
import com.artfil.bookshop.data.AuthorData;
import com.artfil.bookshop.repository.AuthorRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Set;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = BookShopApplication.class)
public class AuthorRepositoryIntegrationTest {

    @Container
    public static PostgreSQLContainer<BookShopPostgresqlContainer> postgreSQLContainer = BookShopPostgresqlContainer.getInstance();

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void containerCheck() {
        Assertions.assertTrue(postgreSQLContainer.isRunning());
    }

    @Test
    @Order(1)
    @Transactional
    void saveAllThenFindAll() {
        List<Author> savedAuthors = authorRepository.saveAll(List.of(AuthorData.AUTHOR_1, AuthorData.AUTHOR_2));
        Assertions.assertEquals(2, savedAuthors.size());

        List<Author> allAuthors = authorRepository.findAll();
        Assertions.assertEquals(2, allAuthors.size());
    }

    @Test
    @Order(2)
    @Transactional
    void saveThenGet() {
        Author savedAuthor = authorRepository.save(AuthorData.AUTHOR_3);
        Assertions.assertEquals(AuthorData.ID_3, savedAuthor.getId());
        Assertions.assertEquals(AuthorData.NAME_1, savedAuthor.getName());

        Author getAuthor = authorRepository.findById(AuthorData.ID_3).orElseThrow();
        Assertions.assertEquals(AuthorData.ID_3, getAuthor.getId());
        Assertions.assertEquals(AuthorData.NAME_1, getAuthor.getName());
    }

    @Test
    @Order(3)
    void findAuthorsByName() {
        authorRepository.saveAll(AuthorData.AUTHORS);

        Set<Author> authors = authorRepository.findByName(AuthorData.NAME_1);
        Assertions.assertEquals(2, authors.size());
    }
}
