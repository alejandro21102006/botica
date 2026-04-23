package com.upeu.clientes.service.impl;

import com.upeu.clientes.dto.ClientesRequest;
import com.upeu.clientes.dto.ClientesResponse;
import com.upeu.clientes.entity.Clientes;
import com.upeu.clientes.exception.ResourceNotFoundException;
import com.upeu.clientes.mapper.ClientesMapper;
import com.upeu.clientes.repository.ClientesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientesServiceImplTest {

    @Mock
    private ClientesRepository clientesRepository;

    @Spy
    private ClientesMapper clientesMapper = new ClientesMapper();

    @InjectMocks
    private ClientesServiceImpl clientesService;

    @Test
    void shouldCreateClientes() {
        ClientesRequest request = ClientesRequest.builder()
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();
        Clientes savedEntity = Clientes.builder()
                .id(1)
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();

        when(clientesRepository.save(any(Clientes.class))).thenReturn(savedEntity);

        ClientesResponse response = clientesService.create(request);

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Laptop");
        assertThat(response.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldThrowWhenClientesNotFound() {
        when(clientesRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientesService.findById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
