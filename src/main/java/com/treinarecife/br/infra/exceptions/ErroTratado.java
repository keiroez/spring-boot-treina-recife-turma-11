package com.treinarecife.br.infra.exceptions;

public record ErroTratado(
    String mensagem,
    int status
    // String mensagemComplementar,
    // String printStackTrace
) {

}
