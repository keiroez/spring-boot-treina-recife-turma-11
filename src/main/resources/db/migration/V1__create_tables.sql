CREATE TABLE produto (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         preco DECIMAL(10, 2) NOT NULL
);

CREATE TABLE pedido (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        data_pedido DATETIME NOT NULL,
                        cliente VARCHAR(255) NOT NULL,
                        produto_id BIGINT UNIQUE,
                        CONSTRAINT fk_pedido_produto FOREIGN KEY (produto_id) REFERENCES produto(id)
);
