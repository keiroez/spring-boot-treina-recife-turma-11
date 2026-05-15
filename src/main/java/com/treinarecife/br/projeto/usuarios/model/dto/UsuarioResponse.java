package com.treinarecife.br.projeto.usuarios.model.dto;

import java.time.LocalDate;

import com.treinarecife.br.projeto.usuarios.model.Usuario;
import com.treinarecife.br.projeto.usuarios.model.enums.StatusUsuario;

public record UsuarioResponse(
     String nome,
     String cpf,
     String email,
     String senha,
     LocalDate dataNascimento,
     StatusUsuario status
) {
    public UsuarioResponse(Usuario usuario) {
        this(
            usuario.getNome(),
            usuario.getCpf(),
            usuario.getEmail(),
            usuario.getSenha(),
            usuario.getDataNascimento(),
            usuario.getStatus()
        );
    }
}
