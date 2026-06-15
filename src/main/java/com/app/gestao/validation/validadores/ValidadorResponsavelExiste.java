package com.app.gestao.validation.validadores;

import com.app.gestao.dto.tarefa.TarefaRequestDTO;
import com.app.gestao.exception.ResourceNotFoundException;
import com.app.gestao.repository.UsuarioRepository;
import com.app.gestao.validation.ValidadorTarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorResponsavelExiste implements ValidadorTarefa {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(TarefaRequestDTO dto) {
        if (!usuarioRepository.existsById(dto.responsavelId())) {
            throw new ResourceNotFoundException(
                    "Responsável não encontrado: id " + dto.responsavelId());
        }
    }
}
