package com.upeu.pagos.mapper;

import com.upeu.pagos.dto.PagosRequest;
import com.upeu.pagos.dto.PagosResponse;
import com.upeu.pagos.entity.Pagos;
import org.springframework.stereotype.Component;

@Component
public class PagosMapper {

    public Pagos toEntity(PagosRequest request) {
        if (request == null) {
            return null;
        }

        return Pagos.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .idCategoria(request.getIdCategoria())
                .build();
    }

    public PagosResponse toResponse(Pagos entity) {
        if (entity == null) {
            return null;
        }

        return PagosResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .idCategoria(entity.getIdCategoria())
                .build();
    }

    public void updateEntityFromRequest(Pagos entity, PagosRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setIdCategoria(request.getIdCategoria());
    }
}
