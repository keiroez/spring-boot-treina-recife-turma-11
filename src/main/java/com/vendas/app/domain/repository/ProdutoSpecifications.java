package com.vendas.app.domain.repository;

import com.vendas.app.domain.models.Produto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

/**
 * Padrão Specification: cada método retorna um critério de filtro isolado e reutilizável.
 * As Specifications podem ser combinadas com .and() / .or() para montar queries dinâmicas
 * sem precisar escrever JPQL ou SQL manualmente.
 */
public class ProdutoSpecifications {

    // Busca parcial e case-insensitive: LIKE '%nome%' ignorando maiúsculas/minúsculas.
    public static Specification<Produto> porNome(String nome) {
        return (root, query, criteriaBuilder) -> {
            if (nome == null || nome.trim().isEmpty()) {
                return criteriaBuilder.conjunction(); // "WHERE 1=1" — não filtra nada
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nome")),
                    "%" + nome.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Produto> precoMaiorOuIgualA(BigDecimal precoMin) {
        return (root, query, criteriaBuilder) -> {
            if (precoMin == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), precoMin);
        };
    }

    public static Specification<Produto> precoMenorOuIgualA(BigDecimal precoMax) {
        return (root, query, criteriaBuilder) -> {
            if (precoMax == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("preco"), precoMax);
        };
    }

    public static Specification<Produto> precoIgual(BigDecimal preco) {
        return (root, query, criteriaBuilder) -> {
            if (preco == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("preco"), preco);
        };
    }
}
