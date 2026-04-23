package com.upeu.auth.service.impl;

import com.upeu.auth.dto.AuthRequest;
import com.upeu.auth.dto.AuthResponse;
import com.upeu.auth.entity.Auth;
import com.upeu.auth.exception.ResourceNotFoundException;
import com.upeu.auth.mapper.AuthMapper;
import com.upeu.auth.repository.AuthRepository;
import com.upeu.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.upeu.auth.client.CatalogoClient;
import com.upeu.auth.dto.CategoriaDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    private final CatalogoClient catalogoClient;

    @Override
    @Transactional
    public AuthResponse create(AuthRequest request) {
        log.info("Iniciando creacion de auth con nombre: {} y idCategoria: {}", request.getNombre(),
                request.getIdCategoria());
        Auth auth = authMapper.toEntity(request);
        Auth savedAuth = authRepository.save(auth);
        log.info("Auth creado exitosamente con ID: {}", savedAuth.getId());
        return authMapper.toResponse(savedAuth);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthResponse> findAll() {
        log.info("Recuperando lista de auth");
        List<AuthResponse> auth = authRepository.findAll()
                .stream()
                .map(authMapper::toResponse)
                .toList();
        log.info("Se encontraron {} auth", auth.size());
        return auth;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse findById(Integer id) {
        log.info("Buscando auth con ID: {}", id);
        Auth auth = getAuthById(id);
        log.info("Auth encontrado: {} (ID: {})", auth.getNombre(), id);
        return authMapper.toResponse(auth);
    }

    @Override
    @Transactional
    public AuthResponse update(Integer id, AuthRequest request) {
        log.info("Iniciando actualizacion de auth ID: {}", id);
        Auth auth = getAuthById(id);
        authMapper.updateEntityFromRequest(auth, request);
        Auth updatedAuth = authRepository.save(auth);
        log.info("Auth ID: {} actualizado exitosamente", id);
        return authMapper.toResponse(updatedAuth);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Iniciando eliminacion de auth ID: {}", id);
        getAuthById(id);
        authRepository.deleteById(id);
        log.info("Auth ID: {} eliminado exitosamente", id);
    }

    private Auth getAuthById(Integer id) {
        return authRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Auth no encontrado: ID {}", id);
                    return new ResourceNotFoundException("Auth con id " + id + " no encontrado");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse findDetalleById(Integer id) {
        log.info("Buscando detalle de auth con ID: {}", id);

        Auth auth = getAuthById(id);

        CategoriaDto categoria = null;

        try {
            categoria = catalogoClient.findCategoriaById(
                    auth.getIdCategoria().longValue());
        } catch (Exception e) {
            log.warn("No se pudo obtener la categoría desde catalogo. idCategoria={}", auth.getIdCategoria(), e);
        }

        return AuthResponse.builder()
                .id(auth.getId())
                .nombre(auth.getNombre())
                .descripcion(auth.getDescripcion())
                .idCategoria(auth.getIdCategoria())
                .categoria(categoria)
                .build();
    }
    /*
     * @Override
     * 
     * @Transactional(readOnly = true)
     * public AuthResponse findDetalleById(Integer id) {
     * log.info("Buscando detalle de auth con ID: {}", id);
     * 
     * Auth auth = getAuthById(id);
     * 
     * CategoriaDto categoria = catalogoClient.findCategoriaById(
     * auth.getIdCategoria().longValue());
     * 
     * return AuthResponse.builder()
     * .id(auth.getId())
     * .nombre(auth.getNombre())
     * .descripcion(auth.getDescripcion())
     * .idCategoria(auth.getIdCategoria())
     * .categoria(categoria)
     * .build();
     * 
     * }
     */
}
