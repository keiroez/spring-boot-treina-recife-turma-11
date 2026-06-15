package com.app.gestao.validation;

import com.app.gestao.dto.tarefa.TarefaRequestDTO;

public interface ValidadorTarefa {
    void validar(TarefaRequestDTO dto);
}
