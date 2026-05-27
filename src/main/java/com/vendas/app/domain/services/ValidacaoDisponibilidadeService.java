package com.vendas.app.domain.services;

import com.vendas.app.domain.models.Pedido;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoDisponibilidadeService implements ValidacaoInclusao {

    @Override
    public void validar(Pedido pedido) {
        if (pedido.getProduto() == null) {
            throw new IllegalArgumentException("O pedido deve estar associado a um produto.");
        }
    }
}
