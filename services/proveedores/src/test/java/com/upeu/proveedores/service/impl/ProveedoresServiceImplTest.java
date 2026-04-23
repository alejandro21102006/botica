package com.upeu.proveedores.service.impl;

import com.upeu.proveedores.dto.ProveedoresRequest;
import com.upeu.proveedores.dto.ProveedoresResponse;
import com.upeu.proveedores.entity.Proveedores;
import com.upeu.proveedores.exception.ResourceNotFoundException;
import com.upeu.proveedores.mapper.ProveedoresMapper;
import com.upeu.proveedores.repository.ProveedoresRepository;
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
class ProveedoresServiceImplTest {

    @Mock
    private ProveedoresRepository proveedoresRepository;

    @Spy
    private ProveedoresMapper proveedoresMapper = new ProveedoresMapper();

    @InjectMocks
    private ProveedoresServiceImpl proveedoresService;

    @Test
    void shouldCreateProveedores() {
        ProveedoresRequest request = ProveedoresRequest.builder()
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();
        Proveedores savedEntity = Proveedores.builder()
                .id(1)
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();

        when(proveedoresRepository.save(any(Proveedores.class))).thenReturn(savedEntity);

        ProveedoresResponse response = proveedoresService.create(request);

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Laptop");
        assertThat(response.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldThrowWhenProveedoresNotFound() {
        when(proveedoresRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> proveedoresService.findById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
