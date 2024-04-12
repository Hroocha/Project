package com.shopproject.product;


import org.testcontainers.containers.PostgreSQLContainer;

public class PsqlContainer extends PostgreSQLContainer<PsqlContainer> {
    private static final String IMAGE_VERSION = "postgres:11.1";
    private static PsqlContainer container;

    private PsqlContainer() {
        super(IMAGE_VERSION);
    }

    public static PsqlContainer getInstance() {
        if (container == null) {
            container = new PsqlContainer();
        }
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
        //do nothing, JVM handles shut down
    }
}