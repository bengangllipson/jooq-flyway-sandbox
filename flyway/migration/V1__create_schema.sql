SET search_path TO sandbox;

CREATE TABLE test_table (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    cool BOOLEAN NOT NULL,
    created_date BIGINT,
    last_modified_date BIGINT,
    optional INTEGER
);
