package com.upeu.auth.mapper;

import com.upeu.auth.dto.AuthRequest;
import com.upeu.auth.dto.AuthResponse;
import com.upeu.auth.entity.Auth;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public Auth toEntity(AuthRequest request) {
        if (request == null) {
            return null;
        }

        return Auth.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .idCategoria(request.getIdCategoria())
                .build();
    }

    public AuthResponse toResponse(Auth entity) {
        if (entity == null) {
            return null;
        }

        return AuthResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .idCategoria(entity.getIdCategoria())
                .build();
    }

    public void updateEntityFromRequest(Auth entity, AuthRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setIdCategoria(request.getIdCategoria());
    }
}
