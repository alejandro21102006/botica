package com.upeu.carrito.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ventas")
public interface VentasClient {

    @PostMapping("/api/v1/ventas/registrar-desde-carrito/{id}")
    String registrarDesdeCarrito(@PathVariable("id") Integer id);
}
