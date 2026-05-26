package com.vendas.app.domain.repository;

import com.vendas.app.domain.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
