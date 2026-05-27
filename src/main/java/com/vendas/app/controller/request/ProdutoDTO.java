package com.vendas.app.controller.request;

import lombok.Data;

import java.math.BigDecimal;

// DTO (Data Transfer Object): objeto que trafega na API — nunca expõe a entidade JPA diretamente.
@Data
public class ProdutoDTO {

    private Long id;
    private String nome;
    private BigDecimal preco;
}
