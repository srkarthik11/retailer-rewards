CREATE TABLE customer (
                          id IDENTITY PRIMARY KEY,
                          name VARCHAR(255)
);

CREATE TABLE transaction (
                             id IDENTITY PRIMARY KEY,
                             customer BIGINT,
                             amount DOUBLE,
                             date DATE
);
