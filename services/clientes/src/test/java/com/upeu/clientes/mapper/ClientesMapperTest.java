package com.upeu.clientes.mapper;

import com.upeu.clientes.dto.ClientesRequest;
import com.upeu.clientes.dto.ClientesResponse;
import com.upeu.clientes.entity.Clientes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientesMapperTest {

    private final ClientesMapper clientesMapper = new ClientesMapper();

    @Test
    void shouldMapRequestToEntity() {
        ClientesRequest request = ClientesRequest.builder()
            .nombre("Laptop")
            .descripcion("Portatil de oficina")
            .idCategoria(3)
                .build();

        Clientes entity = clientesMapper.toEntity(request);

        assertThat(entity).isNotNull();
        assertThat(entity.getNombre()).isEqualTo("Laptop");
        assertThat(entity.getDescripcion()).isEqualTo("Portatil de oficina");
        assertThat(entity.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldMapEntityToResponse() {
        Clientes entity = Clientes.builder()
            .id(1)
            .nombre("Mouse")
            .descripcion("Periferico")
            .idCategoria(4)
                .build();

        ClientesResponse response = clientesMapper.toResponse(entity);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Mouse");
        assertThat(response.getDescripcion()).isEqualTo("Periferico");
        assertThat(response.getIdCategoria()).isEqualTo(4);
    }

    @Test
    void shouldUpdateEntityFromRequest() {
        Clientes entity = Clientes.builder()
            .id(1)
                .nombre("Anterior")
                .descripcion("Anterior descripcion")
            .idCategoria(1)
                .build();
        ClientesRequest request = ClientesRequest.builder()
                .nombre("Nueva")
                .descripcion("Nueva descripcion")
            .idCategoria(2)
                .build();

        clientesMapper.updateEntityFromRequest(entity, request);

        assertThat(entity.getNombre()).isEqualTo("Nueva");
        assertThat(entity.getDescripcion()).isEqualTo("Nueva descripcion");
        assertThat(entity.getIdCategoria()).isEqualTo(2);
    }
}
