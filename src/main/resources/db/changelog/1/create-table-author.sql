create table if not exists author
(
    id   bigint       not null
        constraint author_pkey primary key,
    name varchar(255) not null
);

create index author_name_idx on author (name);