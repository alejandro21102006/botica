package com.upeu.compras.service.impl;

import com.upeu.compras.dto.ComprasRequest;
import com.upeu.compras.dto.ComprasResponse;
import com.upeu.compras.entity.Compras;
import com.upeu.compras.exception.ResourceNotFoundException;
import com.upeu.compras.mapper.ComprasMapper;
import com.upeu.compras.repository.ComprasRepository;
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
class ComprasServiceImplTest {

    @Mock
    private ComprasRepository comprasRepository;

    @Spy
    private ComprasMapper comprasMapper = new ComprasMapper();

    @InjectMocks
    private ComprasServiceImpl comprasService;

    @Test
    void shouldCreateCompras() {
        ComprasRequest request = ComprasRequest.builder()
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();
        Compras savedEntity = Compras.builder()
                .id(1)
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();

        when(comprasRepository.save(any(Compras.class))).thenReturn(savedEntity);

        ComprasResponse response = comprasService.create(request);

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Laptop");
        assertThat(response.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldThrowWhenComprasNotFound() {
        when(comprasRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> comprasService.findById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
