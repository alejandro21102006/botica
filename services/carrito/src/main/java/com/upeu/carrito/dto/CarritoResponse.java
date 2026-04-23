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
public class CarritoResponse {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer idCategoria;
    private CategoriaDto categoria;
}
