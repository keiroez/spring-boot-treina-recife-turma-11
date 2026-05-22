package com.treinarecife.br.usuarios.model.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.treinarecife.br.usuarios.model.enums.StatusUsuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioCreateRequest(
    @NotBlank(message="Nome não pode ser nulo ou vazio")
    String nome,
    @CPF(message="CPF invalido")
    String cpf,
    @Email(message="Email invalido")
    String email,
    @NotBlank(message="Senha nao pode ser nula ou vazia")
    String senha,
    @NotNull(message="Data de nascimento é obrigatória")
    LocalDate dataNascimento,
    @NotNull(message="Status não pode ser nulo")
    StatusUsuario status
) {
    
}
