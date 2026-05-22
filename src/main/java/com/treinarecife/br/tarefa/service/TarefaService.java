package com.treinarecife.br.tarefa.service;

import com.treinarecife.br.projeto.service.ProjetoService;
import com.treinarecife.br.tarefa.model.Tarefa;
import com.treinarecife.br.tarefa.model.dto.TarefaCreateRequest;
import com.treinarecife.br.tarefa.model.dto.TarefaUpdateRequest;
import com.treinarecife.br.tarefa.repository.TarefaRepository;
import com.treinarecife.br.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProjetoService projetoService;


    public Tarefa create(TarefaCreateRequest request) {
        Tarefa tarefa = new Tarefa(request);

        var usuario = usuarioService.findById(request.idUsuario());
        var projeto = projetoService.findById(request.idProjeto());

        tarefa.setUsuario(usuario);
        tarefa.setProjeto(projeto);

        return tarefaRepository.save(tarefa);
    }

    public void update(TarefaUpdateRequest request, Long id){
        var tarefa = tarefaRepository.getReferenceById(id);

        if(request!=null){
            if(request.titulo()!=null){
                tarefa.setTitulo(request.titulo());
            }
            if(request.descricao()!=null){
                tarefa.setDescricao(request.descricao());
            }
            if(request.dataCriacao()!=null){
                tarefa.setDataCriacao(request.dataCriacao());
            }
            if(request.dataConclusao()!=null){
                tarefa.setDataConclusao(request.dataConclusao());
            }
            if(request.prioridade()!=null){
                tarefa.setPrioridade(request.prioridade());
            }
            if(request.status()!=null){
                tarefa.setStatus(request.status());
            }
            this.tarefaRepository.save(tarefa);
        }
    }

    public void delete(Long id){
        this.tarefaRepository.deleteById(id);
    }

    public Tarefa findById(Long id){
        return this.tarefaRepository.getReferenceById(id);
    }

}
