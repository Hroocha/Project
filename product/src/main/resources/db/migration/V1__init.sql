CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE products
(
    id               UUID PRIMARY KEY DEFAULT public.uuid_generate_v4(),
    name             VARCHAR(255) UNIQUE NOT NULL,
    price            NUMERIC             NOT NULL,
    guarantee_period INT                 NOT NULL,
    picture          BYTEA,
    version          INTEGER
);

CREATE TABLE warehouse
(
    id       UUID PRIMARY KEY REFERENCES products (id) NOT NULL,
    quantity INT                                       NOT NULL,
    version  INTEGER
);
