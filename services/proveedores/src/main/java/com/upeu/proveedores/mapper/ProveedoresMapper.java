package com.upeu.proveedores.mapper;

import com.upeu.proveedores.dto.ProveedoresRequest;
import com.upeu.proveedores.dto.ProveedoresResponse;
import com.upeu.proveedores.entity.Proveedores;
import org.springframework.stereotype.Component;

@Component
public class ProveedoresMapper {

    public Proveedores toEntity(ProveedoresRequest request) {
        if (request == null) {
            return null;
        }

        return Proveedores.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .idCategoria(request.getIdCategoria())
                .build();
    }

    public ProveedoresResponse toResponse(Proveedores entity) {
        if (entity == null) {
            return null;
        }

        return ProveedoresResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .idCategoria(entity.getIdCategoria())
                .build();
    }

    public void updateEntityFromRequest(Proveedores entity, ProveedoresRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setIdCategoria(request.getIdCategoria());
    }
}
