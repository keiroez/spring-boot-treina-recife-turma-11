CREATE TABLE usuarios (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(100)  NOT NULL,
    email      VARCHAR(100)  NOT NULL UNIQUE,
    senha      VARCHAR(255)  NOT NULL,
    role       VARCHAR(20)   NOT NULL
);
