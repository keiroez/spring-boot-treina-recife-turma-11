package com.vendas.app;

import com.vendas.app.domain.models.Produto;
import com.vendas.app.domain.repository.ProdutoRepository;
import com.vendas.app.domain.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {


    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto1;
    private Produto produto2;

    @BeforeEach
    void setUp() {
        produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Teclado Mecânico");
        produto1.setPreco(new BigDecimal("150.00"));

        produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Mouse Gamer");
        produto2.setPreco(new BigDecimal("80.00"));
    }

    @Test
    @DisplayName("Deve retornar todos os produtos quando nenhum filtro for informado")
    void deveRetornarTodosOsProdutosQuandoFiltrosForemNulos() {
        // Arrange
        List<Produto> listaProdutos = Arrays.asList(produto1, produto2);

        // Como todos os filtros são nulos, a Spec passada no findAll será null
        when(produtoRepository.findAll((Specification<Produto>) null)).thenReturn(listaProdutos);

        // Act
        List<Produto> resultado = produtoService.buscarProdutosDinamicos(null, null, null, null);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(produtoRepository, times(1)).findAll((Specification<Produto>) null);
    }

    @Test
    @DisplayName("Deve buscar produtos filtrando por nome e preço quando parâmetros forem informados")
    void deveBuscarProdutosComFiltrosPreenchidos() {
        // Arrange
        List<Produto> listaFiltrada = Collections.singletonList(produto1);

        // Quando usamos Specifications dinâmicas, usamos o ArgumentMatchers.any()
        // porque a instância da Spec é criada dentro do método do Service.
        when(produtoRepository.findAll(ArgumentMatchers.<Specification<Produto>>any())).thenReturn(listaFiltrada);

        // Act
        List<Produto> resultado = produtoService.buscarProdutosDinamicos("Teclado", new BigDecimal("100.00"), new BigDecimal("200.00"), null);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Teclado Mecânico", resultado.get(0).getNome());

        // Garante que o repository foi chamado exatamente uma vez com alguma Specification
        verify(produtoRepository, times(1)).findAll(ArgumentMatchers.<Specification<Produto>>any());
    }


}
