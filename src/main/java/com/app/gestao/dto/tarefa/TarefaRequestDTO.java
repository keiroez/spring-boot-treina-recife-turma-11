package com.app.gestao.dto.tarefa;

import com.app.gestao.enums.Prioridade;
import com.app.gestao.enums.StatusTarefa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TarefaRequestDTO(
        @NotBlank @Size(max = 150) String titulo,
        String descricao,
        @NotNull Prioridade prioridade,
        @NotNull StatusTarefa status,
        @NotNull LocalDate dataExecucao,
        @NotNull Long projetoId,
        @NotNull Long responsavelId
) {}
