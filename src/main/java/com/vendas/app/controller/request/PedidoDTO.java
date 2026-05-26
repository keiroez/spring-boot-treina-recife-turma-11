package com.vendas.app.controller.request;

import lombok.Data;

import java.time.LocalDate;


@Data
public class PedidoDTO {
    private Long id;
    private LocalDate dataPedido;
    private String cliente;
}
