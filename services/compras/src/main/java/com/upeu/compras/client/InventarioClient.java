package com.upeu.compras.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventario")
public interface InventarioClient {

    @PostMapping("/api/v1/inventario/{id}/ingresar")
    String ingresar(@PathVariable("id") Integer id);
}
