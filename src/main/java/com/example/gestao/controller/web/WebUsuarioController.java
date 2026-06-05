package com.example.gestao.controller.web;

import com.example.gestao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/app/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class WebUsuarioController {

    @Autowired private UsuarioService usuarioService;

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int page, Model model) {
        var pagina = usuarioService.listarTodos(PageRequest.of(page, 10, Sort.by("nome")));
        model.addAttribute("usuarios", pagina);
        return "usuarios/lista";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            usuarioService.deletar(id);
            ra.addFlashAttribute("sucesso", "Usuário removido com sucesso.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro ao remover usuário: " + e.getMessage());
        }
        return "redirect:/app/usuarios";
    }
}
