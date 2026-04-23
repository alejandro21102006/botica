package com.upeu.compras.dto;

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
public class ComprasResponse {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer idCategoria;
    private CategoriaDto categoria;
}
