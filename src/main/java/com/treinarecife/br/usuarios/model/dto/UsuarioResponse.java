package com.treinarecife.br.usuarios.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.treinarecife.br.usuarios.model.Usuario;
import com.treinarecife.br.usuarios.model.enums.StatusUsuario;

public record UsuarioResponse(
     String nome,
     String cpf,
     String email,
     String senha,
     LocalDate dataNascimento,
     StatusUsuario status,
     List<String> projetos
) {
    public UsuarioResponse(Usuario usuario, List<String> projetos) {
        this(
            usuario.getNome(),
            usuario.getCpf(),
            usuario.getEmail(),
            usuario.getSenha(),
            usuario.getDataNascimento(),
            usuario.getStatus(),
            projetos
        );
    }
}
