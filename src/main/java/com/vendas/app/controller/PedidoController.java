package com.vendas.app.controller;

import com.vendas.app.controller.request.PedidoDTO;
import com.vendas.app.domain.CRUDInterface;
import com.vendas.app.domain.models.Pedido;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/pedidos")
@RestController
public class PedidoController extends AbstractController<Pedido, PedidoDTO> {

    // O Spring injeta automaticamente o CRUDInterface correspondente ao Pedido
    public PedidoController(CRUDInterface<Pedido> crud) {
        // Passamos explicitamente as classes para o construtor pai
        super(crud, Pedido.class, PedidoDTO.class);
    }
}
