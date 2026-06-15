package com.app.gestao.controller;

import com.app.gestao.dto.projeto.ProjetoRequestDTO;
import com.app.gestao.dto.projeto.ProjetoResponseDTO;
import com.app.gestao.service.ProjetoService;
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
@RequestMapping("/projetos")
@Tag(name = "Projetos", description = "Gerenciamento de projetos — requer autenticação JWT")
@SecurityRequirement(name = "bearer-key")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(
        summary = "Criar projeto",
        description = "Cria um novo projeto. Requer perfil USER ou ADMIN.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Projeto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
        }
    )
    public ResponseEntity<ProjetoResponseDTO> criar(@RequestBody @Valid ProjetoRequestDTO dto,
                                                     UriComponentsBuilder uriBuilder) {
        var response = projetoService.criar(dto);
        var uri = uriBuilder.path("/projetos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(
        summary = "Listar projetos",
        description = "Retorna todos os projetos paginados. Ordenação padrão por nome. Requer perfil USER ou ADMIN.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
        }
    )
    public ResponseEntity<Page<ProjetoResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(projetoService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(
        summary = "Buscar projeto por ID",
        description = "Retorna os dados de um projeto específico. Requer perfil USER ou ADMIN.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Projeto encontrado"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
        }
    )
    public ResponseEntity<ProjetoResponseDTO> buscar(
            @Parameter(description = "ID do projeto") @PathVariable Long id) {
        return ResponseEntity.ok(projetoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(
        summary = "Atualizar projeto",
        description = "Atualiza os dados de um projeto existente. Requer perfil USER ou ADMIN.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
        }
    )
    public ResponseEntity<ProjetoResponseDTO> atualizar(
            @Parameter(description = "ID do projeto") @PathVariable Long id,
            @RequestBody @Valid ProjetoRequestDTO dto) {
        return ResponseEntity.ok(projetoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Deletar projeto",
        description = "Remove um projeto do sistema. Requer perfil ADMIN.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Projeto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado — requer ADMIN")
        }
    )
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do projeto") @PathVariable Long id) {
        projetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
