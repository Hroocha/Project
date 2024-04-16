CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE users
(
    id       UUID PRIMARY KEY DEFAULT public.uuid_generate_v4(),
    login    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    name     VARCHAR(255)        NOT NULL,
    mail     VARCHAR(255)        NOT NULL,
    version  INTEGER
);
INSERT INTO users (login, password, name, mail, version)
VALUES ('technicalUser',
        '$2a$10$XA31gdV3roTX9oKM1omPgOgvxgXe80acU2PAH22Sr6wbFrHCAdk3G',
        'technicalUser',
        'technicalUser@noMail.com',
        0);