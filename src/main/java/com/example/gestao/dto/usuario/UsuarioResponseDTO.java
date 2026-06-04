package com.example.gestao.dto.usuario;

import com.example.gestao.entity.Usuario;

public record UsuarioResponseDTO(Long id, String nome, String email, String role) {

    public static UsuarioResponseDTO fromEntity(Usuario u) {
        return new UsuarioResponseDTO(u.getId(), u.getNome(), u.getEmail(), u.getRole());
    }
}
