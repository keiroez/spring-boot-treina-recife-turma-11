package com.treinarecife.br.projeto.model.dto;

import java.time.LocalDate;

import com.treinarecife.br.projeto.model.enums.StatusProjeto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjetoCreateRequest(
        @NotBlank(message="O nome não pode ser vazio ou nulo")
        String nome,
        @NotBlank(message="A descrição não pode ser vazia ou nula")
        String descricao,
        @NotNull(message="Data de inicio nao pode ser nula")
        LocalDate dataInicio,
         @NotNull(message="Data de conclusao nao pode ser nula")
        LocalDate dataConclusao,
        @NotNull(message="status não pode ser nulo")
        StatusProjeto status,
        @NotNull(message="Usuário é obrigatório")
        Long idResponsavel
) {

}
