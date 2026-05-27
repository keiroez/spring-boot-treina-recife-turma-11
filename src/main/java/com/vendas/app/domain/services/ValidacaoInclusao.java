package com.vendas.app.domain.services;

import com.vendas.app.domain.models.Pedido;

/**
 * Padrão Strategy: define o contrato de validação para inclusão de pedidos.
 * Cada classe que implementar essa interface representa uma estratégia de validação
 * independente. O serviço não precisa conhecer os detalhes de cada regra —
 * apenas itera sobre todas as implementações disponíveis e as executa.
 */
public interface ValidacaoInclusao {
    void validar(Pedido pedido);
}
