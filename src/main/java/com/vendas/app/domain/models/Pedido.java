package com.vendas.app.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    private String cliente;

    // ManyToOne: muitos pedidos podem referenciar o mesmo produto.
    // A coluna produto_id fica na tabela pedido (dono do relacionamento).
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
