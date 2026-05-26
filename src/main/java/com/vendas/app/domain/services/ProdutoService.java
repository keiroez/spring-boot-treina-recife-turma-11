package com.vendas.app.domain.services;

import com.vendas.app.domain.CRUDInterface;
import com.vendas.app.domain.models.Produto;
import com.vendas.app.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService  implements CRUDInterface <Produto> {
    private final ProdutoRepository produtoRepository;

    @Override
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public Produto findById(Long id) {
        return produtoRepository.getReferenceById(id);
    }

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    public void update(Produto produto) {
        this.produtoRepository.save(produto);
    }

    @Override
    public void delete(Long id) {
        this.produtoRepository.deleteById(id);
    }
}
