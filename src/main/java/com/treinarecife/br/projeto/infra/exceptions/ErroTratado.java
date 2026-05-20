package com.treinarecife.br.projeto.infra.exceptions;

public record ErroTratado(
    String mensagem,
    int status
    // String mensagemComplementar,
    // String printStackTrace
) {

}
