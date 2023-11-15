create table if not exists author_book
(
    author_id bigint not null references author (id),
    book_id   bigint not null references book (id),
    constraint author_book_pkey primary key (author_id, book_id)
);