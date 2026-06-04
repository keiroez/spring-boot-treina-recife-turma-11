package com.example.gestao.validation;

import com.example.gestao.dto.tarefa.TarefaRequestDTO;

public interface ValidadorTarefa {
    void validar(TarefaRequestDTO dto);
}
