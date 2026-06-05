package com.example.gestao.dto.tarefa;

import com.example.gestao.dto.projeto.ProjetoResponseDTO;
import com.example.gestao.dto.usuario.UsuarioResponseDTO;
import com.example.gestao.entity.Tarefa;
import com.example.gestao.enums.Prioridade;
import com.example.gestao.enums.StatusTarefa;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TarefaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        Prioridade prioridade,
        StatusTarefa status,
        LocalDateTime dataCriacao,
        LocalDate dataExecucao,
        ProjetoResponseDTO projeto,
        UsuarioResponseDTO responsavel
) {
    public static TarefaResponseDTO fromEntity(Tarefa t) {
        return new TarefaResponseDTO(
                t.getId(),
                t.getTitulo(),
                t.getDescricao(),
                t.getPrioridade(),
                t.getStatus(),
                t.getDataCriacao(),
                t.getDataExecucao(),
                ProjetoResponseDTO.fromEntity(t.getProjeto()),
                UsuarioResponseDTO.fromEntity(t.getResponsavel())
        );
    }
}
