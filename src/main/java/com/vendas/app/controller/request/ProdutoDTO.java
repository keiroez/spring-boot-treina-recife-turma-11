package com.vendas.app.controller.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoDTO {

    private Long id;

    private String nome;

    private BigDecimal preco;
}
