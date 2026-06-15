package com.app.gestao.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Email @Size(max = 100) String email,
        @NotBlank @Size(min = 6) String senha,
        @NotBlank @Pattern(regexp = "ROLE_USER|ROLE_ADMIN") String role
) {}
