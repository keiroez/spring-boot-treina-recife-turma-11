package com.auth.user.app.model;

import com.auth.user.app.controller.request.UsuarioResquest;
import com.auth.user.app.controller.response.UsuarioResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
    private String permissao;
    private String email;

    public Usuario(UsuarioResquest usuarioDTO, String senhaCodificada) {
        this.login = usuarioDTO.login();
        this.senha = senhaCodificada;
        this.permissao = usuarioDTO.permissao();
        this.email = usuarioDTO.email();
    }

    public UsuarioResponse toDTO() {
        return new UsuarioResponse(this.id, this.login, this.permissao);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+this.permissao.toUpperCase()));
    }

    @Override
    public String getPassword() {
        return this.senha; // Diz ao Spring onde está a senha criptografada
    }

    @Override
    public String getUsername() {
        return this.email; // Diz ao Spring que o nosso "username" de login é o e-mail
    }
}
