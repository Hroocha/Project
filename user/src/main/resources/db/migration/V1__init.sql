CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE users
(
    id       UUID PRIMARY KEY DEFAULT public.uuid_generate_v4(),
    login    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    name     VARCHAR(255)        NOT NULL,
    mail     VARCHAR(255)        NOT NULL
);