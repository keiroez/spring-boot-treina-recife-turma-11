package com.vendas.app.controller;

import com.vendas.app.controller.request.ProdutoDTO;
import com.vendas.app.domain.CRUDInterface;
import com.vendas.app.domain.models.Produto;
import com.vendas.app.domain.services.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("produtos")
public class ProdutoController extends AbstractController<Produto, ProdutoDTO> {
    private final ProdutoService produtoService;

    protected ProdutoController(CRUDInterface<Produto> crud, ProdutoService produtoService) {
        super(crud, Produto.class, ProdutoDTO.class);
        this.produtoService = produtoService;
    }


    @GetMapping("/page")
    private Page<Produto> findAllPage(@PageableDefault Pageable pageable) {
        return produtoService.findAllPage(pageable);
    }

    @GetMapping("/filtro")
    private List<Produto> findAllFiltroPage(
                                            @RequestParam(required = false) String nome,
                                            @RequestParam(required = false) BigDecimal precoMin,
                                            @RequestParam(required = false) BigDecimal precoMax) {
        return produtoService.buscarProdutosDinamicos(nome, precoMin, precoMax);
    }
}
