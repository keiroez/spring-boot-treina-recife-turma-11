package com.treinarecife.br.projeto.usuarios.model.dto;

import java.time.LocalDate;

import com.treinarecife.br.projeto.usuarios.model.Usuario;
import com.treinarecife.br.projeto.usuarios.model.enums.StatusUsuario;

import lombok.Data;

@Data
public class UsuarioReadDTO {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private StatusUsuario status;

    public UsuarioReadDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.cpf = usuario.getCpf();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.dataNascimento = usuario.getDataNascimento();
        this.status = usuario.getStatus();
    }
}
