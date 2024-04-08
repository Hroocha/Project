CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE purchase
(
    id               UUID PRIMARY KEY DEFAULT public.uuid_generate_v4(),
    user_id          UUID         NOT NULL,
    product_id       UUID         NOT NULL,
    price            NUMERIC      NOT NULL,
    guarantee_period INTEGER      NOT NULL,
    date_of_purchase TIMESTAMP    NOT NULL,
    status           VARCHAR(255) NOT NULL,
    comment          VARCHAR(255),
    version          INTEGER      NOT NULL
);