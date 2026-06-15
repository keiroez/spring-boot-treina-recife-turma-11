package com.app.gestao.service;

import com.app.gestao.dto.projeto.ProjetoRequestDTO;
import com.app.gestao.dto.projeto.ProjetoResponseDTO;
import com.app.gestao.entity.Projeto;
import com.app.gestao.exception.ResourceNotFoundException;
import com.app.gestao.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public ProjetoResponseDTO criar(ProjetoRequestDTO dto) {
        var responsavel = usuarioService.buscarEntidade(dto.responsavelId());
        var projeto = new Projeto();
        projeto.setNome(dto.nome());
        projeto.setDescricao(dto.descricao());
        projeto.setStatus(dto.status());
        projeto.setDataInicio(dto.dataInicio());
        projeto.setDataFim(dto.dataFim());
        projeto.setResponsavel(responsavel);
        return ProjetoResponseDTO.fromEntity(projetoRepository.save(projeto));
    }

    public Page<ProjetoResponseDTO> listarTodos(Pageable pageable) {
        return projetoRepository.findAll(pageable).map(ProjetoResponseDTO::fromEntity);
    }

    public ProjetoResponseDTO buscarPorId(Long id) {
        return ProjetoResponseDTO.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public ProjetoResponseDTO atualizar(Long id, ProjetoRequestDTO dto) {
        var projeto = buscarEntidade(id);
        var responsavel = usuarioService.buscarEntidade(dto.responsavelId());
        projeto.setNome(dto.nome());
        projeto.setDescricao(dto.descricao());
        projeto.setStatus(dto.status());
        projeto.setDataInicio(dto.dataInicio());
        projeto.setDataFim(dto.dataFim());
        projeto.setResponsavel(responsavel);
        return ProjetoResponseDTO.fromEntity(projetoRepository.save(projeto));
    }

    @Transactional
    public void deletar(Long id) {
        buscarEntidade(id);
        projetoRepository.deleteById(id);
    }

    public Projeto buscarEntidade(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado: id " + id));
    }
}
