package com.vendas.app.domain.repository;

import com.vendas.app.domain.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    @Query(
            """
                select p from Produto p where p.preco = :preco
            """
    )
    List<Produto> buscarPorPreco(BigDecimal preco);

}
