package com.treinarecife.br.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treinarecife.br.projeto.model.Projeto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @Query("""
        select p from Projeto p where p.nome = :nome
    """)
    List<Projeto> buscarPorNome(String nome);

    @Query(value = "select * from sgp2.projetos p where p.nome = :nome", nativeQuery = true)
    List<Projeto> buscarPorNomeNativo(@Param("nome") String nome);

    List<Projeto> findByNomeAndDescricaoOrderByNomeAsc(String nome, String descricao);
}