package com.vendas.app.domain.repository;

import com.vendas.app.domain.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
