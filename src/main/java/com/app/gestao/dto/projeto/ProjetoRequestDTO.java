package com.app.gestao.dto.projeto;

import com.app.gestao.enums.StatusProjeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProjetoRequestDTO(
        @NotBlank @Size(max = 150) String nome,
        String descricao,
        @NotNull StatusProjeto status,
        @NotNull LocalDate dataInicio,
        LocalDate dataFim,
        @NotNull Long responsavelId
) {}
