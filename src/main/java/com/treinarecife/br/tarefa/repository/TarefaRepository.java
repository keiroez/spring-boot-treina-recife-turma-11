package com.treinarecife.br.tarefa.repository;

import com.treinarecife.br.tarefa.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa,Long> {
}
