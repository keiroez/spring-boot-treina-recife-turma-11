package com.app.gestao.repository;

import com.app.gestao.entity.Tarefa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Page<Tarefa> findByProjetoId(Long projetoId, Pageable pageable);
    Page<Tarefa> findByResponsavelId(Long responsavelId, Pageable pageable);
}
