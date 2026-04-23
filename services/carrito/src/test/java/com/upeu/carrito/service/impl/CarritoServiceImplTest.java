package com.upeu.carrito.service.impl;

import com.upeu.carrito.dto.CarritoRequest;
import com.upeu.carrito.dto.CarritoResponse;
import com.upeu.carrito.entity.Carrito;
import com.upeu.carrito.exception.ResourceNotFoundException;
import com.upeu.carrito.mapper.CarritoMapper;
import com.upeu.carrito.repository.CarritoRepository;
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
class CarritoServiceImplTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Spy
    private CarritoMapper carritoMapper = new CarritoMapper();

    @InjectMocks
    private CarritoServiceImpl carritoService;

    @Test
    void shouldCreateCarrito() {
        CarritoRequest request = CarritoRequest.builder()
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();
        Carrito savedEntity = Carrito.builder()
                .id(1)
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();

        when(carritoRepository.save(any(Carrito.class))).thenReturn(savedEntity);

        CarritoResponse response = carritoService.create(request);

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Laptop");
        assertThat(response.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldThrowWhenCarritoNotFound() {
        when(carritoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carritoService.findById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
