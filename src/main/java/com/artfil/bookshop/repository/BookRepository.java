package com.artfil.bookshop.repository;

import com.artfil.bookshop.data.Book;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@NonNullApi
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(nativeQuery = true, value = """
            select b.id, b.title
            from book b
            inner join author_book ab on b.id = ab.book_id
            inner join author a on a.id = ab.author_id
            where b.id in
                  (select ab.book_id
                   from author_book ab
                   where ab.author_id =:authorId)""")
    Set<Book> findAllByAuthorId(@Param("authorId") Long authorId);

    @Override
    @EntityGraph(attributePaths = {"authors"})
    List<Book> findAll();
}
