package com.treinarecife.br.projeto.projeto.model;

import java.time.LocalDate;

import com.treinarecife.br.projeto.projeto.model.dto.ProjetoCreateRequest;
import com.treinarecife.br.projeto.projeto.model.dto.ProjetoResponse;
import com.treinarecife.br.projeto.projeto.model.enums.StatusProjeto;
import com.treinarecife.br.projeto.usuarios.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="projetos")
public class Projeto {

    @Id
    @Column(name="idprojetos")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private  Long id;
    private  String nome;
    private  String descricao;
    private  LocalDate dataInicio;
    private  LocalDate dataConclusao;


    @Enumerated(EnumType.STRING)
    private  StatusProjeto status;

    @ManyToOne
    @JoinColumn(name="idusuarios")
    private  Usuario responsavel;

    public Projeto(ProjetoCreateRequest dto, Usuario responsavel) {
        this.nome = dto.nome();
        this.descricao = dto.descricao();
        this.dataInicio = dto.dataInicio();
        this.dataConclusao  =dto.dataConclusao();
        this.status = dto.status();
        this.responsavel = responsavel;
    }

    public ProjetoResponse toDTO(){
        return new ProjetoResponse(
            this.nome,
            this.descricao,
            this.dataInicio,
            this.getDataConclusao()
        );
    }

}
