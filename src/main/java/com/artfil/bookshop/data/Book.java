package com.artfil.bookshop.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    private Long id;

    @Column(name = "title", nullable = false)
    @NotBlank
    @Size(max = 255)
    private String title;

    @ManyToMany(mappedBy = "books")
    @JsonIgnoreProperties("books")
    @Builder.Default
    private Set<Author> authors = new HashSet<>();
}
