package com.upeu.carrito.mapper;

import com.upeu.carrito.dto.CarritoRequest;
import com.upeu.carrito.dto.CarritoResponse;
import com.upeu.carrito.entity.Carrito;
import org.springframework.stereotype.Component;

@Component
public class CarritoMapper {

    public Carrito toEntity(CarritoRequest request) {
        if (request == null) {
            return null;
        }

        return Carrito.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .idCategoria(request.getIdCategoria())
                .build();
    }

    public CarritoResponse toResponse(Carrito entity) {
        if (entity == null) {
            return null;
        }

        return CarritoResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .idCategoria(entity.getIdCategoria())
                .build();
    }

    public void updateEntityFromRequest(Carrito entity, CarritoRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setIdCategoria(request.getIdCategoria());
    }
}
