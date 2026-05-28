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

    // Paginação: o Spring resolve automaticamente os parâmetros ?page=0&size=20&sort=nome
    // a partir do Pageable. O @PageableDefault define os valores padrão.
    @GetMapping("/page")
    public Page<ProdutoDTO> findAllPage(@PageableDefault Pageable pageable) {
        return produtoService.findAllPage(pageable).map(this::convertToDto);
    }

    // Filtros dinâmicos: cada parâmetro é opcional (required = false).
    // Quando nulo, o filtro correspondente simplesmente não é aplicado.
    @GetMapping("/filtro")
    public List<ProdutoDTO> findAllFiltro(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) BigDecimal preco) {
        return produtoService.buscarProdutosDinamicos(nome, precoMin, precoMax, preco)
                .stream()
                .map(this::convertToDto)
                .toList();
    }
}
