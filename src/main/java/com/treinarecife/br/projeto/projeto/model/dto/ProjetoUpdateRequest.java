package com.treinarecife.br.projeto.projeto.model.dto;

import java.time.LocalDate;

import com.treinarecife.br.projeto.projeto.model.enums.StatusProjeto;

public record ProjetoUpdateRequest(
        String nome,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataConclusao,
        StatusProjeto status,
        Long idResponsavel) {


}
