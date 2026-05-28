package com.vendas.app.domain.services;

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
public class ProdutoService implements CRUDInterface<Produto> {

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
        produtoRepository.save(produto);
    }

    @Override
    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }

    public Page<Produto> findAllPage(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    /**
     * Monta a query dinamicamente combinando Specifications com .and().
     * Cada filtro só é adicionado se o parâmetro for não-nulo.
     * Se nenhum filtro for informado, spec permanece null e o findAll(null)
     * do Spring Data traz todos os registros sem lançar erro.
     */
    public List<Produto> buscarProdutosDinamicos(String nome, BigDecimal precoMin, BigDecimal precoMax, BigDecimal preco) {

        Specification<Produto> spec = null;

        if (nome != null && !nome.trim().isEmpty()) {
            spec = ProdutoSpecifications.porNome(nome);
        }

        if (precoMin != null) {
            spec = (spec == null)
                    ? ProdutoSpecifications.precoMaiorOuIgualA(precoMin)
                    : spec.and(ProdutoSpecifications.precoMaiorOuIgualA(precoMin));
        }

        if (precoMax != null) {
            spec = (spec == null)
                    ? ProdutoSpecifications.precoMenorOuIgualA(precoMax)
                    : spec.and(ProdutoSpecifications.precoMenorOuIgualA(precoMax));
        }

        if (preco != null) {
            spec = (spec == null)
                    ? ProdutoSpecifications.precoIgual(preco)
                    : spec.and(ProdutoSpecifications.precoIgual(preco));
        }

        return produtoRepository.findAll(spec);
    }
}
