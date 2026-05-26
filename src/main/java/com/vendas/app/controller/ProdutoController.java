package com.vendas.app.controller;

import com.vendas.app.controller.request.ProdutoDTO;
import com.vendas.app.domain.CRUDInterface;
import com.vendas.app.domain.models.Produto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("produtos")
public class ProdutoController extends AbstractController<Produto, ProdutoDTO> {
    protected ProdutoController(CRUDInterface<Produto> crud) {
        super(crud, Produto.class, ProdutoDTO.class);
    }
}
