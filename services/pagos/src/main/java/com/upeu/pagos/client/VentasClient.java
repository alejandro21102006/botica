package com.upeu.pagos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ventas")
public interface VentasClient {

    @PostMapping("/api/v1/ventas/{id}/confirmar-pago")
    String confirmarPago(@PathVariable("id") Integer id);
}
