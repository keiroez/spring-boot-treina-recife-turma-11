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

import com.treinarecife.br.projeto.usuarios.model.Usuario;
import com.treinarecife.br.projeto.usuarios.model.dto.UsuarioCreateDTO;
import com.treinarecife.br.projeto.usuarios.model.dto.UsuarioReadDTO;
import com.treinarecife.br.projeto.usuarios.model.dto.UsuarioUpdateDTO;
import com.treinarecife.br.projeto.usuarios.service.UsuarioService;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Long create(@RequestBody UsuarioCreateDTO dto) {
        var usuarioCriado = usuarioService.inserir(dto);
        return usuarioCriado.getId();
    }

    @GetMapping("/{id}")
    public UsuarioReadDTO findById(@PathVariable Long id) {
        var usuario = usuarioService.findById(id);
        var dto = new UsuarioReadDTO(usuario);
        return dto;
    }

    @GetMapping("/nome/{nome}")
    public List<UsuarioReadDTO> buscarPorNome(@PathVariable String nome) {
        var usuarios = usuarioService.buscarPorNome(nome);

        List<UsuarioReadDTO> listaRetorno = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioReadDTO usuarioDTO = new UsuarioReadDTO(usuario);
            listaRetorno.add(usuarioDTO);
        }
        return listaRetorno;
    }

    @GetMapping("/cpf/{cpf}")
    public List<UsuarioReadDTO> buscarPorCPF(@PathVariable String cpf) {
        var usuarios = usuarioService.buscarPorCPF(cpf);

        List<UsuarioReadDTO> listaRetorno = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UsuarioReadDTO usuarioDTO = new UsuarioReadDTO(usuario);
            listaRetorno.add(usuarioDTO);
        }
        return listaRetorno;
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody UsuarioUpdateDTO dto) {
        usuarioService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuarioService.delete(id);
    }

}
