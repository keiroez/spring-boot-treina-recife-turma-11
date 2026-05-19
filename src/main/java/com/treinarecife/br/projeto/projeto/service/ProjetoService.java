package com.treinarecife.br.projeto.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinarecife.br.projeto.projeto.respository.ProjetoRepository;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;
}
