package com.artfil.bookshop.common;

import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Objects;

public class BookShopPostgresqlContainer extends PostgreSQLContainer<BookShopPostgresqlContainer> {

    private static final String IMAGE_VERSION = "postgres:latest";

    private static BookShopPostgresqlContainer container;

    private BookShopPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static BookShopPostgresqlContainer getInstance() {
        if (Objects.isNull(container))
            container = new BookShopPostgresqlContainer();

        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
    }
}
