package com.app.gestao.service;

import com.app.gestao.dto.tarefa.TarefaRequestDTO;
import com.app.gestao.dto.tarefa.TarefaResponseDTO;
import com.app.gestao.entity.Tarefa;
import com.app.gestao.exception.ResourceNotFoundException;
import com.app.gestao.repository.TarefaRepository;
import com.app.gestao.validation.ValidadorTarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private List<ValidadorTarefa> validadores;

    @Transactional
    public TarefaResponseDTO criar(TarefaRequestDTO dto) {
        validadores.forEach(v -> v.validar(dto));

        var projeto = projetoService.buscarEntidade(dto.projetoId());
        var responsavel = usuarioService.buscarEntidade(dto.responsavelId());

        var tarefa = new Tarefa();
        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setPrioridade(dto.prioridade());
        tarefa.setStatus(dto.status());
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefa.setDataExecucao(dto.dataExecucao());
        tarefa.setProjeto(projeto);
        tarefa.setResponsavel(responsavel);

        return TarefaResponseDTO.fromEntity(tarefaRepository.save(tarefa));
    }

    public Page<TarefaResponseDTO> listarTodas(Pageable pageable) {
        return tarefaRepository.findAll(pageable).map(TarefaResponseDTO::fromEntity);
    }

    public TarefaResponseDTO buscarPorId(Long id) {
        return TarefaResponseDTO.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public TarefaResponseDTO atualizar(Long id, TarefaRequestDTO dto) {
        validadores.forEach(v -> v.validar(dto));

        var tarefa = buscarEntidade(id);
        var projeto = projetoService.buscarEntidade(dto.projetoId());
        var responsavel = usuarioService.buscarEntidade(dto.responsavelId());

        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setPrioridade(dto.prioridade());
        tarefa.setStatus(dto.status());
        tarefa.setDataExecucao(dto.dataExecucao());
        tarefa.setProjeto(projeto);
        tarefa.setResponsavel(responsavel);

        return TarefaResponseDTO.fromEntity(tarefaRepository.save(tarefa));
    }

    @Transactional
    public void deletar(Long id) {
        buscarEntidade(id);
        tarefaRepository.deleteById(id);
    }

    private Tarefa buscarEntidade(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada: id " + id));
    }
}
