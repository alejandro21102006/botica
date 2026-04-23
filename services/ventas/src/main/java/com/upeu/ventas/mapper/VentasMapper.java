package com.upeu.ventas.mapper;

import com.upeu.ventas.dto.VentasRequest;
import com.upeu.ventas.dto.VentasResponse;
import com.upeu.ventas.entity.Ventas;
import org.springframework.stereotype.Component;

@Component
public class VentasMapper {

    public Ventas toEntity(VentasRequest request) {
        if (request == null) {
            return null;
        }

        return Ventas.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .idCategoria(request.getIdCategoria())
                .build();
    }

    public VentasResponse toResponse(Ventas entity) {
        if (entity == null) {
            return null;
        }

        return VentasResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .idCategoria(entity.getIdCategoria())
                .build();
    }

    public void updateEntityFromRequest(Ventas entity, VentasRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setIdCategoria(request.getIdCategoria());
    }
}
