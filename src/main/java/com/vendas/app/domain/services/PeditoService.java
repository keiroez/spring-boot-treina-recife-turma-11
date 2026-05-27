package com.vendas.app.domain.services;

import com.vendas.app.domain.CRUDInterface;
import com.vendas.app.domain.models.Pedido;
import com.vendas.app.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeditoService implements CRUDInterface<Pedido> {
    private final PedidoRepository pedidoRepository;

    private final List<ValidacaoInclusao> validacoesInclusao;

    @Override
    public Pedido save(Pedido pedido) {

//        for(ValidacaoInclusao validacao : validacoesInclusao){
//            validacao.validar();
//        }

        validacoesInclusao.forEach(validacao -> validacao.validar());

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
