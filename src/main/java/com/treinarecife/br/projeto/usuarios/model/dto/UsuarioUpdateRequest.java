package com.treinarecife.br.projeto.usuarios.model.dto;

public record UsuarioUpdateRequest(
    String nome,
    String cpf,
    String email
) {

}
