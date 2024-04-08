CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE guarantees
(
    id          UUID PRIMARY KEY DEFAULT public.uuid_generate_v4(),
    purchase_id UUID      NOT NULL,
    valid_until TIMESTAMP NOT NULL,
    version     INTEGER
);