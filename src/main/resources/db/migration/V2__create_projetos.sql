CREATE TABLE projetos (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome             VARCHAR(150) NOT NULL,
    descricao        TEXT,
    status           VARCHAR(30)  NOT NULL,
    data_inicio      DATE         NOT NULL,
    data_fim         DATE,
    responsavel_id   BIGINT       NOT NULL,
    CONSTRAINT fk_projeto_usuario FOREIGN KEY (responsavel_id) REFERENCES usuarios(id)
);
