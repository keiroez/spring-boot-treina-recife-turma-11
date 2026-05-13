package com.treinarecife.br.projeto.usuarios.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinarecife.br.projeto.usuarios.UsuarioRepository;
import com.treinarecife.br.projeto.usuarios.model.Usuario;
import com.treinarecife.br.projeto.usuarios.model.dto.UsuarioCreateDTO;
import com.treinarecife.br.projeto.usuarios.model.enums.StatusUsuario;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario inserir(UsuarioCreateDTO usuarioDTO) {
        var novoUsuario = new Usuario(usuarioDTO);

        return usuarioRepository.save(novoUsuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado para o id " + id));
    }

    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.buscarPorNome(nome);
    }

    public List<Usuario> buscarPorCPF(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

}
