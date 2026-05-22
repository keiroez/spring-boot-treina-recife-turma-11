package com.treinarecife.br.usuarios.model.dto;

public record UsuarioUpdateRequest(
    String nome,
    String cpf,
    String email
) {

}
