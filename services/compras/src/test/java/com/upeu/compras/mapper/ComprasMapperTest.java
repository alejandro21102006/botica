package com.upeu.compras.mapper;

import com.upeu.compras.dto.ComprasRequest;
import com.upeu.compras.dto.ComprasResponse;
import com.upeu.compras.entity.Compras;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ComprasMapperTest {

    private final ComprasMapper comprasMapper = new ComprasMapper();

    @Test
    void shouldMapRequestToEntity() {
        ComprasRequest request = ComprasRequest.builder()
            .nombre("Laptop")
            .descripcion("Portatil de oficina")
            .idCategoria(3)
                .build();

        Compras entity = comprasMapper.toEntity(request);

        assertThat(entity).isNotNull();
        assertThat(entity.getNombre()).isEqualTo("Laptop");
        assertThat(entity.getDescripcion()).isEqualTo("Portatil de oficina");
        assertThat(entity.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldMapEntityToResponse() {
        Compras entity = Compras.builder()
            .id(1)
            .nombre("Mouse")
            .descripcion("Periferico")
            .idCategoria(4)
                .build();

        ComprasResponse response = comprasMapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Mouse");
        assertThat(response.getDescripcion()).isEqualTo("Periferico");
        assertThat(response.getIdCategoria()).isEqualTo(4);
    }

    @Test
    void shouldUpdateEntityFromRequest() {
        Compras entity = Compras.builder()
            .id(1)
                .nombre("Anterior")
                .descripcion("Anterior descripcion")
            .idCategoria(1)
                .build();
        ComprasRequest request = ComprasRequest.builder()
                .nombre("Nueva")
                .descripcion("Nueva descripcion")
            .idCategoria(2)
                .build();

        comprasMapper.updateEntityFromRequest(entity, request);

        assertThat(entity.getNombre()).isEqualTo("Nueva");
        assertThat(entity.getDescripcion()).isEqualTo("Nueva descripcion");
        assertThat(entity.getIdCategoria()).isEqualTo(2);
    }
}
