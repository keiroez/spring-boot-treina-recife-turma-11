CREATE TABLE tarefas (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo           VARCHAR(150) NOT NULL,
    descricao        TEXT,
    prioridade       VARCHAR(20)  NOT NULL,
    status           VARCHAR(20)  NOT NULL,
    data_criacao     DATETIME     NOT NULL,
    data_execucao    DATE         NOT NULL,
    projeto_id       BIGINT       NOT NULL,
    responsavel_id   BIGINT       NOT NULL,
    CONSTRAINT fk_tarefa_projeto  FOREIGN KEY (projeto_id)     REFERENCES projetos(id),
    CONSTRAINT fk_tarefa_usuario  FOREIGN KEY (responsavel_id) REFERENCES usuarios(id)
);
