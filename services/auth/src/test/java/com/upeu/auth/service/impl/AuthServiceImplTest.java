package com.upeu.auth.service.impl;

import com.upeu.auth.dto.AuthRequest;
import com.upeu.auth.dto.AuthResponse;
import com.upeu.auth.entity.Auth;
import com.upeu.auth.exception.ResourceNotFoundException;
import com.upeu.auth.mapper.AuthMapper;
import com.upeu.auth.repository.AuthRepository;
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
class AuthServiceImplTest {

    @Mock
    private AuthRepository authRepository;

    @Spy
    private AuthMapper authMapper = new AuthMapper();

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void shouldCreateAuth() {
        AuthRequest request = AuthRequest.builder()
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();
        Auth savedEntity = Auth.builder()
                .id(1)
                .nombre("Laptop")
                .descripcion("Portatil de oficina")
                .idCategoria(3)
                .build();

        when(authRepository.save(any(Auth.class))).thenReturn(savedEntity);

        AuthResponse response = authService.create(request);

        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getNombre()).isEqualTo("Laptop");
        assertThat(response.getIdCategoria()).isEqualTo(3);
    }

    @Test
    void shouldThrowWhenAuthNotFound() {
        when(authRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.findById(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
