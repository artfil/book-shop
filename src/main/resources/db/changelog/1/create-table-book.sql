create table if not exists book
(
    id    bigint       not null
        constraint book_pkey primary key,
    title varchar(255) not null
);