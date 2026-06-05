package com.example.gestao.controller.web;

import com.example.gestao.dto.projeto.ProjetoRequestDTO;
import com.example.gestao.enums.StatusProjeto;
import com.example.gestao.service.ProjetoService;
import com.example.gestao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/app/projetos")
public class WebProjetoController {

    @Autowired private ProjetoService projetoService;
    @Autowired private UsuarioService usuarioService;

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int page, Model model) {
        var pagina = projetoService.listarTodos(PageRequest.of(page, 10, Sort.by("nome")));
        model.addAttribute("projetos", pagina);
        return "projetos/lista";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("projeto", null);
        model.addAttribute("usuarios", usuarioService.listarTodos(PageRequest.of(0, 200, Sort.by("nome"))).getContent());
        model.addAttribute("statusList", StatusProjeto.values());
        return "projetos/form";
    }

    @PostMapping("/novo")
    public String criar(@RequestParam String nome,
                        @RequestParam(required = false) String descricao,
                        @RequestParam StatusProjeto status,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
                        @RequestParam Long responsavelId,
                        RedirectAttributes ra) {
        try {
            var dto = new ProjetoRequestDTO(nome, descricao, status, dataInicio, dataFim, responsavelId);
            projetoService.criar(dto);
            ra.addFlashAttribute("sucesso", "Projeto criado com sucesso.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao criar projeto: " + e.getMessage());
        }
        return "redirect:/app/projetos";
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("projeto", projetoService.buscarPorId(id));
            model.addAttribute("usuarios", usuarioService.listarTodos(PageRequest.of(0, 200, Sort.by("nome"))).getContent());
            model.addAttribute("statusList", StatusProjeto.values());
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Projeto não encontrado.");
            return "redirect:/app/projetos";
        }
        return "projetos/form";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(@PathVariable Long id,
                            @RequestParam String nome,
                            @RequestParam(required = false) String descricao,
                            @RequestParam StatusProjeto status,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
                            @RequestParam Long responsavelId,
                            RedirectAttributes ra) {
        try {
            var dto = new ProjetoRequestDTO(nome, descricao, status, dataInicio, dataFim, responsavelId);
            projetoService.atualizar(id, dto);
            ra.addFlashAttribute("sucesso", "Projeto atualizado com sucesso.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao atualizar projeto: " + e.getMessage());
        }
        return "redirect:/app/projetos";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            projetoService.deletar(id);
            ra.addFlashAttribute("sucesso", "Projeto removido com sucesso.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao remover projeto: " + e.getMessage());
        }
        return "redirect:/app/projetos";
    }
}
