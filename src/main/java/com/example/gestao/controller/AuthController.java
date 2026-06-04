package com.example.gestao.controller;

import com.example.gestao.dto.auth.LoginRequestDTO;
import com.example.gestao.dto.auth.TokenResponseDTO;
import com.example.gestao.entity.Usuario;
import com.example.gestao.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        var auth = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var authentication = authenticationManager.authenticate(auth);
        var token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}
