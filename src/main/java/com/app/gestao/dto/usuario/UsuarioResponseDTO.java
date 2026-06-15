package com.app.gestao.dto.usuario;

import com.app.gestao.entity.Usuario;

public record UsuarioResponseDTO(Long id, String nome, String email, String role) {

    public static UsuarioResponseDTO fromEntity(Usuario u) {
        return new UsuarioResponseDTO(u.getId(), u.getNome(), u.getEmail(), u.getRole());
    }
}
