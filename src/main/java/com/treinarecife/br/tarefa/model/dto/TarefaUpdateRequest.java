package com.treinarecife.br.tarefa.model.dto;

import com.treinarecife.br.tarefa.model.enums.Prioridades;
import com.treinarecife.br.tarefa.model.enums.StatusProjeto;

import java.time.LocalDate;

public record TarefaUpdateRequest(
        String titulo,
        String descricao,
        LocalDate dataCriacao,
        LocalDate dataConclusao,
        Prioridades prioridade,
        StatusProjeto status,
        // Em Records/DTOs, expomos os dados relevantes do relacionamento.
        // Você pode passar o ID do projeto/usuário ou criar um Record aninhado simples.
        Long idProjeto,
        Long idUsuario
) {
}
