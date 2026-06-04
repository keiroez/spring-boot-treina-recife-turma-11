package com.example.gestao.controller;

import com.example.gestao.dto.projeto.ProjetoRequestDTO;
import com.example.gestao.dto.projeto.ProjetoResponseDTO;
import com.example.gestao.service.ProjetoService;
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
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProjetoResponseDTO> criar(@RequestBody @Valid ProjetoRequestDTO dto,
                                                     UriComponentsBuilder uriBuilder) {
        var response = projetoService.criar(dto);
        var uri = uriBuilder.path("/projetos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<ProjetoResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(projetoService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProjetoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProjetoResponseDTO> atualizar(@PathVariable Long id,
                                                         @RequestBody @Valid ProjetoRequestDTO dto) {
        return ResponseEntity.ok(projetoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        projetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
