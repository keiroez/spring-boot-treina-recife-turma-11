package com.treinarecife.br.tarefa.model;

import com.treinarecife.br.projeto.model.Projeto;
import com.treinarecife.br.tarefa.model.dto.TarefaCreateRequest;
import com.treinarecife.br.tarefa.model.enums.Prioridades;
import com.treinarecife.br.tarefa.model.enums.StatusProjeto;
import com.treinarecife.br.usuarios.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtarefas")
    private Long id;

    private String titulo;

    private String descricao;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    @Enumerated(EnumType.STRING)
    private Prioridades prioridade;

    @Enumerated(EnumType.STRING)
    private StatusProjeto status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprojetos")
    private Projeto projeto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuarios")
    private Usuario usuario;

    public Tarefa(TarefaCreateRequest request) {
        this.titulo = request.titulo();
        this.descricao = request.descricao();
        this.dataCriacao = request.dataCriacao();
        this.dataConclusao = request.dataConclusao();
        this.prioridade = request.prioridade();
        this.status = request.status();
    }
}
