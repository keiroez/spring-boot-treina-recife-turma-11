package com.vendas.app.domain.services;

import com.vendas.app.domain.CRUDInterface;
import com.vendas.app.domain.models.Pedido;
import com.vendas.app.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService implements CRUDInterface<Pedido> {

    private final PedidoRepository pedidoRepository;

    /**
     * O Spring injeta aqui, automaticamente, todos os beans que implementam
     * ValidacaoInclusao (ValidacaoNomeVazio, ValidarPrecoService, etc.).
     * Isso é o padrão Strategy: o serviço não conhece as regras — apenas as executa.
     */
    private final List<ValidacaoInclusao> validacoesInclusao;

    @Override
    public Pedido save(Pedido pedido) {
        validacoesInclusao.forEach(validacao -> validacao.validar(pedido));
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.getReferenceById(id);
    }

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public void update(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    @Override
    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }
}
