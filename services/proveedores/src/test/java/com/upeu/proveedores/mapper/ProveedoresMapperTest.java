package com.upeu.proveedores.mapper;

import com.upeu.proveedores.dto.ProveedoresRequest;
import com.upeu.proveedores.dto.ProveedoresResponse;
import com.upeu.proveedores.entity.Proveedores;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProveedoresMapperTest {

    private final ProveedoresMapper proveedoresMapper = new ProveedoresMapper();

    @Test
    void shouldMapRequestToEntity() {
        ProveedoresRequest request = ProveedoresRequest.builder()
            .nombre("Laptop")
            .descripcion("Portatil de oficina")
            .idCategoria(3)
                .build();

        Proveedores entity = proveedoresMapper.toEntity(request);

        assertThat(entity).isNotNull();
        assertThat(entity.getNombre()).isEqualTo("Laptop");
        assertThat(entity.getDescripcion()).isEqualTo("Portatil de oficina");
        assertThat(entity.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldMapEntityToResponse() {
        Proveedores entity = Proveedores.builder()
            .id(1)
            .nombre("Mouse")
            .descripcion("Periferico")
            .idCategoria(4)
                .build();

        ProveedoresResponse response = proveedoresMapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Mouse");
        assertThat(response.getDescripcion()).isEqualTo("Periferico");
        assertThat(response.getIdCategoria()).isEqualTo(4);
    }

    @Test
    void shouldUpdateEntityFromRequest() {
        Proveedores entity = Proveedores.builder()
            .id(1)
                .nombre("Anterior")
                .descripcion("Anterior descripcion")
            .idCategoria(1)
                .build();
        ProveedoresRequest request = ProveedoresRequest.builder()
                .nombre("Nueva")
                .descripcion("Nueva descripcion")
            .idCategoria(2)
                .build();

        proveedoresMapper.updateEntityFromRequest(entity, request);

        assertThat(entity.getNombre()).isEqualTo("Nueva");
        assertThat(entity.getDescripcion()).isEqualTo("Nueva descripcion");
        assertThat(entity.getIdCategoria()).isEqualTo(2);
    }
}
