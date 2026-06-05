package com.example.gestao.controller;

import com.example.gestao.dto.tarefa.TarefaRequestDTO;
import com.example.gestao.dto.tarefa.TarefaResponseDTO;
import com.example.gestao.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tarefas", description = "Gerenciamento de tarefas vinculadas a projetos — requer autenticação JWT")
@SecurityRequirement(name = "bearer-key")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(
        summary = "Criar tarefa",
        description = "Cria uma nova tarefa vinculada a um projeto EM_ANDAMENTO com responsável existente. Requer perfil USER ou ADMIN.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
        }
    )
    public ResponseEntity<TarefaResponseDTO> criar(@RequestBody @Valid TarefaRequestDTO dto,
                                                    UriComponentsBuilder uriBuilder) {
        var response = tarefaService.criar(dto);
        var uri = uriBuilder.path("/tarefas/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(
        summary = "Listar tarefas",
        description = "Retorna todas as tarefas paginadas. Ordenação padrão por dataExecucao. Requer perfil USER ou ADMIN.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
        }
    )
    public ResponseEntity<Page<TarefaResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "dataExecucao") Pageable pageable) {
        return ResponseEntity.ok(tarefaService.listarTodas(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(
        summary = "Buscar tarefa por ID",
        description = "Retorna os dados de uma tarefa específica. Requer perfil USER ou ADMIN.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
        }
    )
    public ResponseEntity<TarefaResponseDTO> buscar(
            @Parameter(description = "ID da tarefa") @PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(
        summary = "Atualizar tarefa",
        description = "Atualiza os dados de uma tarefa existente. Requer perfil USER ou ADMIN.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
        }
    )
    public ResponseEntity<TarefaResponseDTO> atualizar(
            @Parameter(description = "ID da tarefa") @PathVariable Long id,
            @RequestBody @Valid TarefaRequestDTO dto) {
        return ResponseEntity.ok(tarefaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Deletar tarefa",
        description = "Remove uma tarefa do sistema. Requer perfil ADMIN.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Tarefa removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado — requer ADMIN")
        }
    )
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da tarefa") @PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
