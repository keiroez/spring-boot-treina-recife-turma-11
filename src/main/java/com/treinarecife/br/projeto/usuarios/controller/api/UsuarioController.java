package com.treinarecife.br.projeto.usuarios.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treinarecife.br.projeto.projeto.model.Projeto;
import com.treinarecife.br.projeto.usuarios.model.Usuario;
import com.treinarecife.br.projeto.usuarios.model.dto.UsuarioCreateRequest;
import com.treinarecife.br.projeto.usuarios.model.dto.UsuarioResponse;
import com.treinarecife.br.projeto.usuarios.model.dto.UsuarioUpdateRequest;
import com.treinarecife.br.projeto.usuarios.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Long create(@Valid @RequestBody UsuarioCreateRequest dto) {
        var usuarioCriado = usuarioService.inserir(dto);
        return usuarioCriado.getId();
    }

    @GetMapping("/{id}")
    public UsuarioResponse findById(@PathVariable Long id) {
        var usuario = usuarioService.findById(id);

        List<Projeto> projetos = usuario.getListaProjetos();
        List<String> descricoes = new ArrayList<>();

        for (Projeto projeto : projetos) {
            descricoes.add(projeto.getDescricao());
        }

        var dto = new UsuarioResponse(usuario, descricoes);
        return dto;
    }

    @GetMapping("/nome/{nome}")
    public List<UsuarioResponse> buscarPorNome(@PathVariable String nome) {
        var usuarios = usuarioService.buscarPorNome(nome);

        List<UsuarioResponse> listaRetorno = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioResponse usuarioDTO = new UsuarioResponse(usuario, usuario.montarDescricaoProjetos());
            listaRetorno.add(usuarioDTO);
        }
        return listaRetorno;
    }

    @GetMapping("/cpf/{cpf}")
    public List<UsuarioResponse> buscarPorCPF(@PathVariable String cpf) {
        var usuarios = usuarioService.buscarPorCPF(cpf);

        List<UsuarioResponse> listaRetorno = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioResponse usuarioDTO = new UsuarioResponse(usuario, usuario.montarDescricaoProjetos());
            listaRetorno.add(usuarioDTO);
        }
        return listaRetorno;
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody UsuarioUpdateRequest dto) {
        usuarioService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuarioService.delete(id);
    }

}
