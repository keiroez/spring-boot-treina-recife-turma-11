CREATE TABLE IF NOT EXISTS projetos (
    idprojetos INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NULL,
    data_inicio DATE NOT NULL,
    data_conclusao DATE NULL,
    status VARCHAR(10) NOT NULL,
    idusuarios INT NOT NULL,
    PRIMARY KEY(idprojetos),
    FOREIGN KEY (idusuarios) REFERENCES usuarios(idusuarios)
);
CREATE INDEX idusuarios ON projetos(idusuarios);
