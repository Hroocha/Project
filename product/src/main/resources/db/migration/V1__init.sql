CREATE TABLE products
(
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name             VARCHAR(255) UNIQUE NOT NULL,
    price            INT                 NOT NULL,
    guarantee_period TIMESTAMP           NOT NULL,
    picture          BYTEA
);

CREATE TABLE warehouse
(
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_id       UUID REFERENCES products(id) NOT NULL,
    quantity         INT NOT NULL
);