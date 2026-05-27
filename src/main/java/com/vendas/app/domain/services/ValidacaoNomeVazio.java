package com.vendas.app.domain.services;

import com.vendas.app.domain.models.Pedido;
import org.springframework.stereotype.Component;

// @Component: torna essa classe um bean Spring, injetada automaticamente
// na lista de ValidacaoInclusao do PedidoService.
@Component
public class ValidacaoNomeVazio implements ValidacaoInclusao {

    @Override
    public void validar(Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().isBlank()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
    }
}
