package com.vendas.app.domain.repository;

import com.vendas.app.domain.models.Produto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProdutoSpecifications {
    // Filtro por Nome (Case-Insensitive e busca parcial)
    public static Specification<Produto> porNome(String nome) {
        return (root, query, criteriaBuilder) -> {
            if (nome == null || nome.trim().isEmpty()) {
                return criteriaBuilder.conjunction(); // Retorna um "where 1=1" (não filtra nada)
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nome")),
                    "%" + nome.toLowerCase() + "%"
            );
        };
    }

    // Filtro por Preço Mínimo
    public static Specification<Produto> precoMaiorOuIgualA(BigDecimal precoMin) {
        return (root, query, criteriaBuilder) -> {
            if (precoMin == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), precoMin);
        };
    }

    // Filtro por Preço Máximo
    public static Specification<Produto> precoMenorOuIgualA(BigDecimal precoMax) {
        return (root, query, criteriaBuilder) -> {
            if (precoMax == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("preco"), precoMax);
        };
    }
}
