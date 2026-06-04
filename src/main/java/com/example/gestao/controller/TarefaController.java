package com.example.gestao.controller;

import com.example.gestao.dto.tarefa.TarefaRequestDTO;
import com.example.gestao.dto.tarefa.TarefaResponseDTO;
import com.example.gestao.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TarefaResponseDTO> criar(@RequestBody @Valid TarefaRequestDTO dto,
                                                    UriComponentsBuilder uriBuilder) {
        var response = tarefaService.criar(dto);
        var uri = uriBuilder.path("/tarefas/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<TarefaResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "dataExecucao") Pageable pageable) {
        return ResponseEntity.ok(tarefaService.listarTodas(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TarefaResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TarefaResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid TarefaRequestDTO dto) {
        return ResponseEntity.ok(tarefaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
