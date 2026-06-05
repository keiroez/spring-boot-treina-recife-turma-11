package com.example.gestao.controller.web;

import com.example.gestao.dto.tarefa.TarefaRequestDTO;
import com.example.gestao.enums.Prioridade;
import com.example.gestao.enums.StatusTarefa;
import com.example.gestao.service.ProjetoService;
import com.example.gestao.service.TarefaService;
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
@RequestMapping("/app/tarefas")
public class WebTarefaController {

    @Autowired private TarefaService tarefaService;
    @Autowired private ProjetoService projetoService;
    @Autowired private UsuarioService usuarioService;

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int page, Model model) {
        var pagina = tarefaService.listarTodas(PageRequest.of(page, 10, Sort.by("dataExecucao")));
        model.addAttribute("tarefas", pagina);
        return "tarefas/lista";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("tarefa", null);
        model.addAttribute("projetos", projetoService.listarTodos(PageRequest.of(0, 200, Sort.by("nome"))).getContent());
        model.addAttribute("usuarios", usuarioService.listarTodos(PageRequest.of(0, 200, Sort.by("nome"))).getContent());
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("statusList", StatusTarefa.values());
        return "tarefas/form";
    }

    @PostMapping("/novo")
    public String criar(@RequestParam String titulo,
                        @RequestParam(required = false) String descricao,
                        @RequestParam Prioridade prioridade,
                        @RequestParam StatusTarefa status,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataExecucao,
                        @RequestParam Long projetoId,
                        @RequestParam Long responsavelId,
                        RedirectAttributes ra) {
        try {
            var dto = new TarefaRequestDTO(titulo, descricao, prioridade, status, dataExecucao, projetoId, responsavelId);
            tarefaService.criar(dto);
            ra.addFlashAttribute("sucesso", "Tarefa criada com sucesso.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao criar tarefa: " + e.getMessage());
        }
        return "redirect:/app/tarefas";
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("tarefa", tarefaService.buscarPorId(id));
            model.addAttribute("projetos", projetoService.listarTodos(PageRequest.of(0, 200, Sort.by("nome"))).getContent());
            model.addAttribute("usuarios", usuarioService.listarTodos(PageRequest.of(0, 200, Sort.by("nome"))).getContent());
            model.addAttribute("prioridades", Prioridade.values());
            model.addAttribute("statusList", StatusTarefa.values());
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Tarefa não encontrada.");
            return "redirect:/app/tarefas";
        }
        return "tarefas/form";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(@PathVariable Long id,
                            @RequestParam String titulo,
                            @RequestParam(required = false) String descricao,
                            @RequestParam Prioridade prioridade,
                            @RequestParam StatusTarefa status,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataExecucao,
                            @RequestParam Long projetoId,
                            @RequestParam Long responsavelId,
                            RedirectAttributes ra) {
        try {
            var dto = new TarefaRequestDTO(titulo, descricao, prioridade, status, dataExecucao, projetoId, responsavelId);
            tarefaService.atualizar(id, dto);
            ra.addFlashAttribute("sucesso", "Tarefa atualizada com sucesso.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao atualizar tarefa: " + e.getMessage());
        }
        return "redirect:/app/tarefas";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            tarefaService.deletar(id);
            ra.addFlashAttribute("sucesso", "Tarefa removida com sucesso.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao remover tarefa: " + e.getMessage());
        }
        return "redirect:/app/tarefas";
    }
}
