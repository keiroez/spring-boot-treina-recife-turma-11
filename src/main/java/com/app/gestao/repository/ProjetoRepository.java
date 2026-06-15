package com.app.gestao.repository;

import com.app.gestao.entity.Projeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    Page<Projeto> findByResponsavelId(Long responsavelId, Pageable pageable);
}
