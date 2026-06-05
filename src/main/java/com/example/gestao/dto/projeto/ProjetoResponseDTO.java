package com.example.gestao.dto.projeto;

import com.example.gestao.dto.usuario.UsuarioResponseDTO;
import com.example.gestao.entity.Projeto;
import com.example.gestao.enums.StatusProjeto;

import java.time.LocalDate;

public record ProjetoResponseDTO(
        Long id,
        String nome,
        String descricao,
        StatusProjeto status,
        LocalDate dataInicio,
        LocalDate dataFim,
        UsuarioResponseDTO responsavel
) {
    public static ProjetoResponseDTO fromEntity(Projeto p) {
        return new ProjetoResponseDTO(
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getStatus(),
                p.getDataInicio(),
                p.getDataFim(),
                UsuarioResponseDTO.fromEntity(p.getResponsavel())
        );
    }
}
