package com.treinarecife.br.projeto.projeto.model.dto;

import java.time.LocalDate;

public record ProjetoResponse(
    String nome,
    String descricao,
    LocalDate dataInicio,
    LocalDate dataConclusao
) {

}
