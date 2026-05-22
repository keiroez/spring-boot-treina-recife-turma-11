package com.treinarecife.br.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinarecife.br.usuarios.model.Usuario;
import com.treinarecife.br.usuarios.model.dto.UsuarioCreateRequest;
import com.treinarecife.br.usuarios.model.dto.UsuarioUpdateRequest;
import com.treinarecife.br.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario inserir(UsuarioCreateRequest usuarioDTO) {
        var novoUsuario = new Usuario(usuarioDTO);

        return usuarioRepository.save(novoUsuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.getReferenceById(id);
    }

    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.buscarPorNome(nome);
    }

    public List<Usuario> buscarPorCPF(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    public void update(UsuarioUpdateRequest dto, Long id) {
        var usuario = this.findById(id);

        if(usuario!=null){
            if(dto.nome()!=null){
                usuario.setNome(dto.nome());
            }
            if(dto.cpf()!=null){
                usuario.setCpf(dto.cpf());
            }
            if(dto.email()!=null){
                usuario.setEmail(dto.email());
            }
            usuarioRepository.save(usuario);
        }

        
    }

    public void delete(Long id) {
        // Outra opcao verificando existencia
        // var usuario = this.findById(id);
        // this.usuarioRepository.delete(usuario);
        this.usuarioRepository.deleteById(id);
    }

}
