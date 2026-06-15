package com.app.gestao.service;

import com.app.gestao.dto.usuario.UsuarioRequestDTO;
import com.app.gestao.dto.usuario.UsuarioResponseDTO;
import com.app.gestao.entity.Usuario;
import com.app.gestao.exception.ResourceNotFoundException;
import com.app.gestao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }

    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        var usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setRole(dto.role());
        return UsuarioResponseDTO.fromEntity(usuarioRepository.save(usuario));
    }

    public Page<UsuarioResponseDTO> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(UsuarioResponseDTO::fromEntity);
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        return UsuarioResponseDTO.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        var usuario = buscarEntidade(id);
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setRole(dto.role());
        return UsuarioResponseDTO.fromEntity(usuarioRepository.save(usuario));
    }

    @Transactional
    public void deletar(Long id) {
        buscarEntidade(id);
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarEntidade(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: id " + id));
    }
}
