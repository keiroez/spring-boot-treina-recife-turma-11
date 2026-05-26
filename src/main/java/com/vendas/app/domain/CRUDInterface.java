package com.vendas.app.domain;

import java.util.List;

public interface CRUDInterface <T> {
    T save(T t);
    T findById(Long id);
    List<T> findAll();
    void update(T t);
    void delete(Long id);
}
