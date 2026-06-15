package com.app.gestao.validation.validadores;

import com.app.gestao.dto.tarefa.TarefaRequestDTO;
import com.app.gestao.enums.StatusProjeto;
import com.app.gestao.exception.ResourceNotFoundException;
import com.app.gestao.exception.UsuarioOcupadoException;
import com.app.gestao.repository.ProjetoRepository;
import com.app.gestao.validation.ValidadorTarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorProjetoAtivo implements ValidadorTarefa {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Override
    public void validar(TarefaRequestDTO dto) {
        var projeto = projetoRepository.findById(dto.projetoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Projeto não encontrado: id " + dto.projetoId()));

        if (projeto.getStatus() != StatusProjeto.EM_ANDAMENTO) {
            throw new UsuarioOcupadoException(
                    "Tarefas só podem ser criadas em projetos com status EM_ANDAMENTO. " +
                    "Status atual: " + projeto.getStatus());
        }
    }
}
