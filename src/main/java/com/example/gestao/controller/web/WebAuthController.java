package com.example.gestao.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/app")
public class WebAuthController {

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String erro,
                        @RequestParam(required = false) String logout,
                        Model model) {
        if (erro != null) model.addAttribute("erro", "Email ou senha inválidos.");
        if (logout != null) model.addAttribute("msg", "Logout realizado com sucesso.");
        return "auth/login";
    }
}
