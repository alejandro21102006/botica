package com.upeu.ventas.service.impl;

import com.upeu.ventas.dto.VentasRequest;
import com.upeu.ventas.dto.VentasResponse;
import com.upeu.ventas.entity.Ventas;
import com.upeu.ventas.exception.ResourceNotFoundException;
import com.upeu.ventas.mapper.VentasMapper;
import com.upeu.ventas.repository.VentasRepository;
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
class VentasServiceImplTest {

    @Mock
    private VentasRepository ventasRepository;

    @Spy
    private VentasMapper ventasMapper = new VentasMapper();

    @InjectMocks
    private VentasServiceImpl ventasService;

    @Test
    void shouldCreateVentas() {
        VentasRequest request = VentasRequest.builder()
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();
        Ventas savedEntity = Ventas.builder()
                .id(1)
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();

        when(ventasRepository.save(any(Ventas.class))).thenReturn(savedEntity);

        VentasResponse response = ventasService.create(request);

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Laptop");
        assertThat(response.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldThrowWhenVentasNotFound() {
        when(ventasRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ventasService.findById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
