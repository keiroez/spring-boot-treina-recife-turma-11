package com.treinarecife.br.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinarecife.br.projeto.model.Projeto;
import com.treinarecife.br.projeto.model.dto.ProjetoCreateRequest;
import com.treinarecife.br.projeto.model.dto.ProjetoUpdateRequest;
import com.treinarecife.br.projeto.repository.ProjetoRepository;
import com.treinarecife.br.usuarios.model.Usuario;
import com.treinarecife.br.usuarios.service.UsuarioService;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private UsuarioService usuarioService;


    public Projeto create(ProjetoCreateRequest dto){
        var usuario = usuarioService.findById(dto.idResponsavel());
        Projeto projetoNovo = new Projeto(dto, usuario);
        return this.projetoRepository.save(projetoNovo);
    }
    

    public Projeto findById(Long id){
        return projetoRepository.getReferenceById(id);
    }

    public void update(Long id, ProjetoUpdateRequest dto){
        var projeto = this.projetoRepository.findById(id).get();

        Usuario usuario = null;
        if(dto.idResponsavel()!=null){
            usuario = usuarioService.findById(id);
        }

        if (dto.nome()!=null) {
            System.out.println(dto.nome());
            projeto.setNome(dto.nome());
        }
        if (dto.descricao()!=null) {
            projeto.setDescricao(dto.descricao());
        }
        if (dto.dataInicio()!=null) {
            projeto.setDataInicio(dto.dataInicio());
        }
        if (dto.dataConclusao()!=null) {
            projeto.setDataConclusao(dto.dataConclusao());
        }
        if (dto.status()!=null) {
            projeto.setStatus(dto.status());
        }
        if (usuario!=null) {
            projeto.setResponsavel(usuario);
        }

        projetoRepository.save(projeto);
    }


    public void delete(Long id) {
        this.projetoRepository.deleteById(id);
    }
}
