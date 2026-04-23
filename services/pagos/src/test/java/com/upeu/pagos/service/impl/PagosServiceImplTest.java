package com.upeu.pagos.service.impl;

import com.upeu.pagos.dto.PagosRequest;
import com.upeu.pagos.dto.PagosResponse;
import com.upeu.pagos.entity.Pagos;
import com.upeu.pagos.exception.ResourceNotFoundException;
import com.upeu.pagos.mapper.PagosMapper;
import com.upeu.pagos.repository.PagosRepository;
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
class PagosServiceImplTest {

    @Mock
    private PagosRepository pagosRepository;

    @Spy
    private PagosMapper pagosMapper = new PagosMapper();

    @InjectMocks
    private PagosServiceImpl pagosService;

    @Test
    void shouldCreatePagos() {
        PagosRequest request = PagosRequest.builder()
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();
        Pagos savedEntity = Pagos.builder()
                .id(1)
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();

        when(pagosRepository.save(any(Pagos.class))).thenReturn(savedEntity);

        PagosResponse response = pagosService.create(request);

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Laptop");
        assertThat(response.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldThrowWhenPagosNotFound() {
        when(pagosRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pagosService.findById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
