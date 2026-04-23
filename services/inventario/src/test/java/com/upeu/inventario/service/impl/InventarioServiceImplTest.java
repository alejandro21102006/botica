package com.upeu.inventario.service.impl;

import com.upeu.inventario.dto.InventarioRequest;
import com.upeu.inventario.dto.InventarioResponse;
import com.upeu.inventario.entity.Inventario;
import com.upeu.inventario.exception.ResourceNotFoundException;
import com.upeu.inventario.mapper.InventarioMapper;
import com.upeu.inventario.repository.InventarioRepository;
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
class InventarioServiceImplTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Spy
    private InventarioMapper inventarioMapper = new InventarioMapper();

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    @Test
    void shouldCreateInventario() {
        InventarioRequest request = InventarioRequest.builder()
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();
        Inventario savedEntity = Inventario.builder()
                .id(1)
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();

        when(inventarioRepository.save(any(Inventario.class))).thenReturn(savedEntity);

        InventarioResponse response = inventarioService.create(request);

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Laptop");
        assertThat(response.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldThrowWhenInventarioNotFound() {
        when(inventarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inventarioService.findById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
