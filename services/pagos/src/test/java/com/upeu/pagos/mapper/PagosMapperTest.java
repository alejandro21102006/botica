package com.upeu.pagos.mapper;

import com.upeu.pagos.dto.PagosRequest;
import com.upeu.pagos.dto.PagosResponse;
import com.upeu.pagos.entity.Pagos;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PagosMapperTest {

    private final PagosMapper pagosMapper = new PagosMapper();

    @Test
    void shouldMapRequestToEntity() {
        PagosRequest request = PagosRequest.builder()
            .nombre("Laptop")
            .descripcion("Portatil de oficina")
            .idCategoria(3)
                .build();

        Pagos entity = pagosMapper.toEntity(request);

        assertThat(entity).isNotNull();
        assertThat(entity.getNombre()).isEqualTo("Laptop");
        assertThat(entity.getDescripcion()).isEqualTo("Portatil de oficina");
        assertThat(entity.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldMapEntityToResponse() {
        Pagos entity = Pagos.builder()
            .id(1)
            .nombre("Mouse")
            .descripcion("Periferico")
            .idCategoria(4)
                .build();

        PagosResponse response = pagosMapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Mouse");
        assertThat(response.getDescripcion()).isEqualTo("Periferico");
        assertThat(response.getIdCategoria()).isEqualTo(4);
    }

    @Test
    void shouldUpdateEntityFromRequest() {
        Pagos entity = Pagos.builder()
            .id(1)
                .nombre("Anterior")
                .descripcion("Anterior descripcion")
            .idCategoria(1)
                .build();
        PagosRequest request = PagosRequest.builder()
                .nombre("Nueva")
                .descripcion("Nueva descripcion")
            .idCategoria(2)
                .build();

        pagosMapper.updateEntityFromRequest(entity, request);

        assertThat(entity.getNombre()).isEqualTo("Nueva");
        assertThat(entity.getDescripcion()).isEqualTo("Nueva descripcion");
        assertThat(entity.getIdCategoria()).isEqualTo(2);
    }
}
