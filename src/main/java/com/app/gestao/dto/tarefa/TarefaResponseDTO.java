package com.app.gestao.dto.tarefa;

import com.app.gestao.dto.projeto.ProjetoResponseDTO;
import com.app.gestao.dto.usuario.UsuarioResponseDTO;
import com.app.gestao.entity.Tarefa;
import com.app.gestao.enums.Prioridade;
import com.app.gestao.enums.StatusTarefa;

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
