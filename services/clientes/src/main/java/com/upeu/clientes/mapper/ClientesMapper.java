package com.upeu.clientes.mapper;

import com.upeu.clientes.dto.ClientesRequest;
import com.upeu.clientes.dto.ClientesResponse;
import com.upeu.clientes.entity.Clientes;
import org.springframework.stereotype.Component;

@Component
public class ClientesMapper {

    public Clientes toEntity(ClientesRequest request) {
        if (request == null) {
            return null;
        }

        return Clientes.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .idCategoria(request.getIdCategoria())
                .build();
    }

    public ClientesResponse toResponse(Clientes entity) {
        if (entity == null) {
            return null;
        }

        return ClientesResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .idCategoria(entity.getIdCategoria())
                .build();
    }

    public void updateEntityFromRequest(Clientes entity, ClientesRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setIdCategoria(request.getIdCategoria());
    }
}
