package com.upeu.clientes.dto;

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
public class ClientesResponse {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer idCategoria;
    private CategoriaDto categoria;
}
