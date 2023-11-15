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
@Table(name = "author")
public class Author {

    @Id
    @SequenceGenerator(name = "author_seq", sequenceName = "author_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(max = 255)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    @JsonIgnoreProperties("authors")
    @Builder.Default
    private Set<Book> books = new HashSet<>();
}
