package com.example.gestao.controller.web;

import com.example.gestao.service.ProjetoService;
import com.example.gestao.service.TarefaService;
import com.example.gestao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app/dashboard")
public class WebDashboardController {

    @Autowired private ProjetoService projetoService;
    @Autowired private TarefaService tarefaService;
    @Autowired private UsuarioService usuarioService;

    @GetMapping
    public String dashboard(Model model) {
        var projetos = projetoService.listarTodos(PageRequest.of(0, 5, Sort.by("nome")));
        var tarefas = tarefaService.listarTodas(PageRequest.of(0, 5, Sort.by("dataExecucao")));
        var usuarios = usuarioService.listarTodos(PageRequest.of(0, 1, Sort.by("nome")));

        model.addAttribute("totalProjetos", projetos.getTotalElements());
        model.addAttribute("totalTarefas", tarefas.getTotalElements());
        model.addAttribute("totalUsuarios", usuarios.getTotalElements());
        model.addAttribute("recentesProjetos", projetos.getContent());
        model.addAttribute("recentesTarefas", tarefas.getContent());
        return "dashboard/index";
    }
}
