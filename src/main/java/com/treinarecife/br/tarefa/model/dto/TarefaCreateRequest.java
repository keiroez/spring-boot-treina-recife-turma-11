package com.treinarecife.br.tarefa.model.dto;

import com.treinarecife.br.projeto.model.Projeto;
import com.treinarecife.br.tarefa.model.enums.Prioridades;
import com.treinarecife.br.tarefa.model.enums.StatusProjeto;
import com.treinarecife.br.usuarios.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TarefaCreateRequest(
        @NotBlank(message = "Titulo não pode ser vazio ou nulo")
        String titulo,
        @NotBlank(message = "Descricao não pode ser vazio ou nulo")
        String descricao,
        @NotNull(message = "Data de criação nao pode ser nula")
        LocalDate dataCriacao,
        @NotNull(message = "Data de conclusao nao pode ser nula")
        LocalDate dataConclusao,
        @NotNull(message = "Prioridade nao pode ser nula")
        Prioridades prioridade,
        @NotNull(message = "Status nao pode ser status")
        StatusProjeto status,
        // Em Records/DTOs, expomos os dados relevantes do relacionamento.
        // Você pode passar o ID do projeto/usuário ou criar um Record aninhado simples.
        @NotNull(message = "Projeto é obrigatorio")
        Long idProjeto,
        @NotNull(message = "Responsável é obrigatorio")
        Long idUsuario
) {
}
