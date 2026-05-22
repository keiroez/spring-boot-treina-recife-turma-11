package com.treinarecife.br.tarefa.model.dto;

import com.treinarecife.br.tarefa.model.Tarefa;
import com.treinarecife.br.tarefa.model.enums.Prioridades;
import com.treinarecife.br.tarefa.model.enums.StatusProjeto;

import java.time.LocalDate;

public record TarefaResponse(
        String titulo,
        String descricao,
        LocalDate dataCriacao,
        LocalDate dataConclusao,
        Prioridades prioridade,
        StatusProjeto status,
        // Em Records/DTOs, expomos os dados relevantes do relacionamento.
        // Você pode passar o ID do projeto/usuário ou criar um Record aninhado simples.
        String nomeProjeto,
        String nomeUsuario
) {
    public TarefaResponse(Tarefa tarefa) {
        this(
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getDataCriacao(),
                tarefa.getDataConclusao(),
                tarefa.getPrioridade(),
                tarefa.getStatus(),
                tarefa.getProjeto().getDescricao(),
                tarefa.getUsuario().getNome()
        );
    }
}
