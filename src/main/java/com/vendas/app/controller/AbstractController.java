package com.vendas.app.controller;

import com.vendas.app.domain.CRUDInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller genérico que centraliza os endpoints de CRUD.
 *
 * Generics: <T> é a entidade JPA, <D> é o DTO correspondente.
 * O construtor recebe Class<T> e Class<D> porque o ModelMapper precisa
 * da referência em tempo de execução para fazer o mapeamento — Java apaga
 * informações de tipo genérico em runtime (type erasure), então precisamos
 * passar explicitamente.
 */
@RestController
public abstract class AbstractController<T, D> {

    private final CRUDInterface<T> crud;

    // O ModelMapper converte automaticamente campos com o mesmo nome entre entidade e DTO.
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

    @GetMapping
    public List<D> findAll() {
        return crud.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public D findById(@PathVariable Long id) {
        return convertToDto(crud.findById(id));
    }

    @PostMapping
    public D save(@RequestBody D dto) {
        T entity = convertToEntity(dto);
        T savedEntity = crud.save(entity);
        return convertToDto(savedEntity);
    }

    @PutMapping
    public void update(@RequestBody D dto) {
        crud.update(convertToEntity(dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        crud.delete(id);
    }
}
