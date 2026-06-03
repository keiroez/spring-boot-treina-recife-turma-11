package com.auth.user.app.controller;

import com.auth.user.app.controller.request.AutenticacaoRequest;
import com.auth.user.app.controller.response.TokenResponse;
import com.auth.user.app.model.Usuario;
import com.auth.user.app.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class AutenticacaoController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<TokenResponse> autenticar(AutenticacaoRequest dto){
// 1. Converte o DTO para o token interno do Spring Security
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());

        // 2. O manager chama o UserDetailsService e valida a senha criptografada no banco
        var authentication = authenticationManager.authenticate(authenticationToken);

        // 3. Se passou, fazemos o cast do principal para o nosso modelo Usuario e geramos o JWT
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        // 4. Devolvemos o token envelopado no DTO de resposta
        return ResponseEntity.ok(new TokenResponse(tokenJWT));
    }

}
