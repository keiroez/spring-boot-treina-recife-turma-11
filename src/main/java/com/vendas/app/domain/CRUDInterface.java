package com.vendas.app.domain;

import java.util.List;

/**
 * Contrato genérico de CRUD. Qualquer serviço que implemente essa interface
 * garante as operações básicas para o tipo T.
 * O AbstractController depende apenas dessa interface — não da implementação
 * concreta — o que facilita trocar ou estender o serviço sem alterar o controller.
 */
public interface CRUDInterface<T> {
    T save(T t);
    T findById(Long id);
    List<T> findAll();
    void update(T t);
    void delete(Long id);
}
