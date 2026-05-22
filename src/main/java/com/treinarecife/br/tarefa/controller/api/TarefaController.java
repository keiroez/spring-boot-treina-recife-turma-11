package com.treinarecife.br.tarefa.controller.api;

import com.treinarecife.br.tarefa.model.dto.TarefaCreateRequest;
import com.treinarecife.br.tarefa.model.dto.TarefaResponse;
import com.treinarecife.br.tarefa.model.dto.TarefaUpdateRequest;
import com.treinarecife.br.tarefa.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tarefas")
@RequiredArgsConstructor
public class TarefaController {
    private final TarefaService tarefaService;

    //Outra forma de injetar dependencia
//    public TarefaController(TarefaService tarefaService) {
//        this.tarefaService = tarefaService;
//    }

    @PostMapping
    public Long create(@Valid @RequestBody TarefaCreateRequest request){
        return this.tarefaService.create(request).getId();
    }

    @PutMapping("/{id}")
    public void update(@RequestBody TarefaUpdateRequest request, @PathVariable Long id){
        this.tarefaService.update(request, id);
    }

    @GetMapping("/{id}")
    public TarefaResponse findById(@PathVariable Long id){
        var tarefa =  this.tarefaService.findById(id);
        return new TarefaResponse(tarefa);
    }

    @DeleteMapping(("/{id}"))
    public void delete(@PathVariable Long id){
        this.tarefaService.delete(id);
    }

}
