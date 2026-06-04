package com.example.gestao.validation.validadores;

import com.example.gestao.dto.tarefa.TarefaRequestDTO;
import com.example.gestao.exception.ResourceNotFoundException;
import com.example.gestao.repository.UsuarioRepository;
import com.example.gestao.validation.ValidadorTarefa;
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
