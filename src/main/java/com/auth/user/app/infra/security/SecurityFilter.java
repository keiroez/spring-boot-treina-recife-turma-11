package com.auth.user.app.infra.security;

import com.auth.user.app.repository.UsuarioRepository;
import com.auth.user.app.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Extrai o token vindo no cabeçalho Authorization
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            // 2. Valida o token e extrai o e-mail (Subject)
            var email = tokenService.validarToken(tokenJWT);

            // 3. Se o e-mail for válido, busca as credenciais e permissões (UserDetails) no MySQL
            var usuario = repository.findByEmail(email);

            if (usuario != null) {
                // 4. Cria o objeto de autenticação do Spring preenchido com as Roles do banco
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                // 5. Força a autenticação no contexto atual da requisição Stateless
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 6. Continua a execução do fluxo normal da API
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", ""); // Remove o prefixo para pegar só o hash
        }
        return null;
    }
}
