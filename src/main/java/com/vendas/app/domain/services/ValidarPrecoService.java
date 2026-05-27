package com.vendas.app.domain.services;

import com.vendas.app.domain.models.Pedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidarPrecoService implements ValidacaoInclusao {

    @Override
    public void validar(Pedido pedido) {
        if (pedido.getProduto() == null
                || pedido.getProduto().getPreco() == null
                || pedido.getProduto().getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O produto associado deve ter um preço válido maior que zero.");
        }
    }
}
