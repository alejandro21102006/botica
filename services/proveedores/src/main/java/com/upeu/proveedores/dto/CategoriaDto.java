package com.upeu.proveedores.dto;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDto {
    private Long id;
    private String nombre;
    private String descripcion;
}
