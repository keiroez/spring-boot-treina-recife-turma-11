package com.treinarecife.br.usuarios.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.treinarecife.br.projeto.model.Projeto;
import com.treinarecife.br.usuarios.model.dto.UsuarioCreateRequest;
import com.treinarecife.br.usuarios.model.enums.StatusUsuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuarios")
    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private String senha;

    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private StatusUsuario status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuarios")
    private List<Projeto> listaProjetos;

    public Usuario(UsuarioCreateRequest dto) {
        this.nome = dto.nome();
        this.cpf = dto.cpf();
        this.email = dto.email();
        this.senha = dto.senha();
        this.dataNascimento = dto.dataNascimento();
        this.status = dto.status();
    }

    public List<String> montarDescricaoProjetos() {
        if (this.getListaProjetos() != null) {
            return this.getListaProjetos().stream()
                    .map(Projeto::getDescricao)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
