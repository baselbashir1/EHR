CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstname  VARCHAR(255)        NOT NULL,
    lastname   VARCHAR(255)        NOT NULL,
    username   VARCHAR(255) UNIQUE NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    phone      VARCHAR(255)        NOT NULL,
    role       VARCHAR(255)        NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);