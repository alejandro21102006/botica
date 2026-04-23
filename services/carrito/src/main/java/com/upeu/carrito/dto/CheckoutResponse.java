package com.upeu.carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {
    private Integer carritoId;
    private boolean inventarioReservado;
    private boolean ventaGenerada;
    private String mensaje;
}
