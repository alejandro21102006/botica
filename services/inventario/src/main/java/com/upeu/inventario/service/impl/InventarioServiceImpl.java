package com.upeu.inventario.service.impl;

import com.upeu.inventario.dto.InventarioRequest;
import com.upeu.inventario.dto.InventarioResponse;
import com.upeu.inventario.entity.Inventario;
import com.upeu.inventario.exception.ResourceNotFoundException;
import com.upeu.inventario.mapper.InventarioMapper;
import com.upeu.inventario.repository.InventarioRepository;
import com.upeu.inventario.service.InventarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.upeu.inventario.client.CatalogoClient;
import com.upeu.inventario.dto.CategoriaDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;
    private final InventarioMapper inventarioMapper;
    private final CatalogoClient catalogoClient;

    @Override
    @Transactional
    public InventarioResponse create(InventarioRequest request) {
        log.info("Iniciando creacion de inventario con nombre: {} y idCategoria: {}", request.getNombre(),
                request.getIdCategoria());
        Inventario inventario = inventarioMapper.toEntity(request);
        Inventario savedInventario = inventarioRepository.save(inventario);
        log.info("Inventario creado exitosamente con ID: {}", savedInventario.getId());
        return inventarioMapper.toResponse(savedInventario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventarioResponse> findAll() {
        log.info("Recuperando lista de inventarios");
        List<InventarioResponse> inventarios = inventarioRepository.findAll()
                .stream()
                .map(inventarioMapper::toResponse)
                .toList();
        log.info("Se encontraron {} inventarios", inventarios.size());
        return inventarios;
    }

    @Override
    @Transactional(readOnly = true)
    public InventarioResponse findById(Integer id) {
        log.info("Buscando inventario con ID: {}", id);
        Inventario inventario = getInventarioById(id);
        log.info("Inventario encontrado: {} (ID: {})", inventario.getNombre(), id);
        return inventarioMapper.toResponse(inventario);
    }

    @Override
    @Transactional
    public InventarioResponse update(Integer id, InventarioRequest request) {
        log.info("Iniciando actualizacion de inventario ID: {}", id);
        Inventario inventario = getInventarioById(id);
        inventarioMapper.updateEntityFromRequest(inventario, request);
        Inventario updatedInventario = inventarioRepository.save(inventario);
        log.info("Inventario ID: {} actualizado exitosamente", id);
        return inventarioMapper.toResponse(updatedInventario);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Iniciando eliminacion de inventario ID: {}", id);
        getInventarioById(id);
        inventarioRepository.deleteById(id);
        log.info("Inventario ID: {} eliminado exitosamente", id);
    }

    private Inventario getInventarioById(Integer id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Inventario no encontrado: ID {}", id);
                    return new ResourceNotFoundException("Inventario con id " + id + " no encontrado");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public InventarioResponse findDetalleById(Integer id) {
        log.info("Buscando detalle de inventario con ID: {}", id);

        Inventario inventario = getInventarioById(id);

        CategoriaDto categoria = null;

        try {
            categoria = catalogoClient.findCategoriaById(
                    inventario.getIdCategoria().longValue());
        } catch (Exception e) {
            log.warn("No se pudo obtener la categoría desde catalogo. idCategoria={}", inventario.getIdCategoria(), e);
        }

        return InventarioResponse.builder()
                .id(inventario.getId())
                .nombre(inventario.getNombre())
                .descripcion(inventario.getDescripcion())
                .idCategoria(inventario.getIdCategoria())
                .categoria(categoria)
                .build();
    }
    /*
     * @Override
     * 
     * @Transactional(readOnly = true)
     * public InventarioResponse findDetalleById(Integer id) {
     * log.info("Buscando detalle de inventario con ID: {}", id);
     * 
     * Inventario inventario = getInventarioById(id);
     * 
     * CategoriaDto categoria = catalogoClient.findCategoriaById(
     * inventario.getIdCategoria().longValue());
     * 
     * return InventarioResponse.builder()
     * .id(inventario.getId())
     * .nombre(inventario.getNombre())
     * .descripcion(inventario.getDescripcion())
     * .idCategoria(inventario.getIdCategoria())
     * .categoria(categoria)
     * .build();
     * 
     * }
     */
}
