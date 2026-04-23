package com.upeu.compras.mapper;

import com.upeu.compras.dto.ComprasRequest;
import com.upeu.compras.dto.ComprasResponse;
import com.upeu.compras.entity.Compras;
import org.springframework.stereotype.Component;

@Component
public class ComprasMapper {

    public Compras toEntity(ComprasRequest request) {
        if (request == null) {
            return null;
        }

        return Compras.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .idCategoria(request.getIdCategoria())
                .build();
    }

    public ComprasResponse toResponse(Compras entity) {
        if (entity == null) {
            return null;
        }

        return ComprasResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .idCategoria(entity.getIdCategoria())
                .build();
    }

    public void updateEntityFromRequest(Compras entity, ComprasRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setIdCategoria(request.getIdCategoria());
    }
}
