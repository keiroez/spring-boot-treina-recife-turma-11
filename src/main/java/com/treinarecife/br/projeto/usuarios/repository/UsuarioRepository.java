package com.treinarecife.br.projeto.usuarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.treinarecife.br.projeto.usuarios.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
            select u from Usuario u where u.nome = :nome
            """)
    List<Usuario> buscarPorNome(String nome);

    List<Usuario> findByNome(String nome);

    List<Usuario> findByCpf(String cpf);

    List<Usuario> findByNomeAndCpf(String nome, String cpf);

    @Query(value = "select * from sgp2.usuarios u where u.nome = :nome", nativeQuery = true)
    List<Usuario> buscarPorNomeNative(@Param("nome") String nome);

}
