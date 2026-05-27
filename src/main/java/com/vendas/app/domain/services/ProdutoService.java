package com.vendas.app.domain.services;

import com.vendas.app.controller.request.ProdutoDTO;
import com.vendas.app.domain.CRUDInterface;
import com.vendas.app.domain.models.Produto;
import com.vendas.app.domain.repository.ProdutoRepository;
import com.vendas.app.domain.repository.ProdutoSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public Page<Produto> findAllPage(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public List<Produto> buscarProdutosDinamicos(String nome, BigDecimal precoMin, BigDecimal precoMax) {

        Specification<Produto> spec = null;

        // Se o nome não for nulo, cria ou combina a especificação
        if (nome != null && !nome.trim().isEmpty()) {
            spec = (spec == null) ? ProdutoSpecifications.porNome(nome) : spec.and(ProdutoSpecifications.porNome(nome));
        }

        if (precoMin != null) {
            spec = (spec == null) ? ProdutoSpecifications.precoMaiorOuIgualA(precoMin) : spec.and(ProdutoSpecifications.precoMaiorOuIgualA(precoMin));
        }

        if (precoMax != null) {
            spec = (spec == null) ? ProdutoSpecifications.precoMenorOuIgualA(precoMax) : spec.and(ProdutoSpecifications.precoMenorOuIgualA(precoMax));
        }

        // Se nenhum filtro foi passado (spec continua null), o findAll(null) traz todos os registros sem dar erro
        return produtoRepository.findAll(spec);
    }
}
