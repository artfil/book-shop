package com.artfil.bookshop.data;

import java.util.List;

public class AuthorData {

    public static final long ID_1 = 1;
    public static final long ID_2 = 2;
    public static final long ID_3 = 3;
    public static final String NAME_1 = "author_1";
    public static final String NAME_2 = "author_2";
    public static final Author AUTHOR_1 = Author.builder().id(ID_1).name(NAME_1).build();
    public static final Author AUTHOR_2 = Author.builder().id(ID_2).name(NAME_2).build();
    public static final Author AUTHOR_3 = Author.builder().id(ID_3).name(NAME_1).build();
    public static final List<Author> AUTHORS = List.of(AUTHOR_1, AUTHOR_2, AUTHOR_3);
}