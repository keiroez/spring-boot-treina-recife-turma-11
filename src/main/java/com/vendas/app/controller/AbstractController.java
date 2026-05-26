package com.vendas.app.controller;

import com.vendas.app.domain.CRUDInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public abstract class AbstractController<T, D> {

    private final CRUDInterface<T> crud; // Removido o @Autowired daqui

    @Autowired
    protected ModelMapper modelMapper;

    private final Class<T> entityClass;
    private final Class<D> dtoClass;

    protected AbstractController(CRUDInterface<T> crud, Class<T> entityClass, Class<D> dtoClass) {
        this.crud = crud;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    public D convertToDto(T entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, dtoClass);
    }

    public T convertToEntity(D dto) {
        if (dto == null) return null;
        return modelMapper.map(dto, entityClass);
    }

    @GetMapping("/{id}")
    public D findById(@PathVariable Long id) {
        T entity = crud.findById(id);
        return convertToDto(entity);
    }

    @PostMapping
    public D save(@RequestBody D dto) {
        T entity = convertToEntity(dto);
        T savedEntity = crud.save(entity);
        return convertToDto(savedEntity);
    }

    @PutMapping
    public void update(@RequestBody D dto) {
        T entity = convertToEntity(dto);
        crud.update(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        crud.delete(id);
    }
}