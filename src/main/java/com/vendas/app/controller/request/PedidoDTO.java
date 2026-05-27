package com.vendas.app.controller.request;

import lombok.Data;

import java.time.LocalDate;

// O ModelMapper preenche o campo 'produto' automaticamente a partir do relacionamento
// @ManyToOne de Pedido, mapeando os campos de nome igual (id, nome, preco).
@Data
public class PedidoDTO {
    private Long id;
    private LocalDate dataPedido;
    private String cliente;
    private ProdutoDTO produto;
}
