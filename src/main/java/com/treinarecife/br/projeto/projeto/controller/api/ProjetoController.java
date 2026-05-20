package com.treinarecife.br.projeto.projeto.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treinarecife.br.projeto.projeto.model.dto.ProjetoCreateRequest;
import com.treinarecife.br.projeto.projeto.model.dto.ProjetoResponse;
import com.treinarecife.br.projeto.projeto.model.dto.ProjetoUpdateRequest;
import com.treinarecife.br.projeto.projeto.service.ProjetoService;

@RestController
@RequestMapping("projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    public Long create(ProjetoCreateRequest request){
        var novoProjeto = projetoService.create(request);
        return novoProjeto.getId();
    }

    @GetMapping("/{id}")
    public ProjetoResponse findById(@PathVariable Long id){
        var projeto = projetoService.findById(id);
        return projeto.toDTO();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody ProjetoUpdateRequest request){
        this.projetoService.update(id, request);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        this.projetoService.delete(id);
    }

}
