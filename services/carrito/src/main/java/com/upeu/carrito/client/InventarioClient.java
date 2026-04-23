package com.upeu.carrito.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventario")
public interface InventarioClient {

    @PostMapping("/api/v1/inventario/{id}/reservar")
    String reservar(@PathVariable("id") Integer id);
}
