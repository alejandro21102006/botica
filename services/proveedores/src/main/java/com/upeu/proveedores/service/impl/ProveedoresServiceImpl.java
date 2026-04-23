package com.upeu.proveedores.service.impl;

import com.upeu.proveedores.dto.ProveedoresRequest;
import com.upeu.proveedores.dto.ProveedoresResponse;
import com.upeu.proveedores.entity.Proveedores;
import com.upeu.proveedores.exception.ResourceNotFoundException;
import com.upeu.proveedores.mapper.ProveedoresMapper;
import com.upeu.proveedores.repository.ProveedoresRepository;
import com.upeu.proveedores.service.ProveedoresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.upeu.proveedores.client.CatalogoClient;
import com.upeu.proveedores.dto.CategoriaDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProveedoresServiceImpl implements ProveedoresService {

    private final ProveedoresRepository proveedoresRepository;
    private final ProveedoresMapper proveedoresMapper;
    private final CatalogoClient catalogoClient;

    @Override
    @Transactional
    public ProveedoresResponse create(ProveedoresRequest request) {
        log.info("Iniciando creacion de proveedores con nombre: {} y idCategoria: {}", request.getNombre(),
                request.getIdCategoria());
        Proveedores proveedores = proveedoresMapper.toEntity(request);
        Proveedores savedProveedores = proveedoresRepository.save(proveedores);
        log.info("Proveedores creado exitosamente con ID: {}", savedProveedores.getId());
        return proveedoresMapper.toResponse(savedProveedores);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedoresResponse> findAll() {
        log.info("Recuperando lista de proveedores");
        List<ProveedoresResponse> proveedores = proveedoresRepository.findAll()
                .stream()
                .map(proveedoresMapper::toResponse)
                .toList();
        log.info("Se encontraron {} proveedores", proveedores.size());
        return proveedores;
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedoresResponse findById(Integer id) {
        log.info("Buscando proveedores con ID: {}", id);
        Proveedores proveedores = getProveedoresById(id);
        log.info("Proveedores encontrado: {} (ID: {})", proveedores.getNombre(), id);
        return proveedoresMapper.toResponse(proveedores);
    }

    @Override
    @Transactional
    public ProveedoresResponse update(Integer id, ProveedoresRequest request) {
        log.info("Iniciando actualizacion de proveedores ID: {}", id);
        Proveedores proveedores = getProveedoresById(id);
        proveedoresMapper.updateEntityFromRequest(proveedores, request);
        Proveedores updatedProveedores = proveedoresRepository.save(proveedores);
        log.info("Proveedores ID: {} actualizado exitosamente", id);
        return proveedoresMapper.toResponse(updatedProveedores);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Iniciando eliminacion de proveedores ID: {}", id);
        getProveedoresById(id);
        proveedoresRepository.deleteById(id);
        log.info("Proveedores ID: {} eliminado exitosamente", id);
    }

    private Proveedores getProveedoresById(Integer id) {
        return proveedoresRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Proveedores no encontrado: ID {}", id);
                    return new ResourceNotFoundException("Proveedores con id " + id + " no encontrado");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedoresResponse findDetalleById(Integer id) {
        log.info("Buscando detalle de proveedores con ID: {}", id);

        Proveedores proveedores = getProveedoresById(id);

        CategoriaDto categoria = null;

        try {
            categoria = catalogoClient.findCategoriaById(
                    proveedores.getIdCategoria().longValue());
        } catch (Exception e) {
            log.warn("No se pudo obtener la categoría desde catalogo. idCategoria={}", proveedores.getIdCategoria(), e);
        }

        return ProveedoresResponse.builder()
                .id(proveedores.getId())
                .nombre(proveedores.getNombre())
                .descripcion(proveedores.getDescripcion())
                .idCategoria(proveedores.getIdCategoria())
                .categoria(categoria)
                .build();
    }
    /*
     * @Override
     * 
     * @Transactional(readOnly = true)
     * public ProveedoresResponse findDetalleById(Integer id) {
     * log.info("Buscando detalle de proveedores con ID: {}", id);
     * 
     * Proveedores proveedores = getProveedoresById(id);
     * 
     * CategoriaDto categoria = catalogoClient.findCategoriaById(
     * proveedores.getIdCategoria().longValue());
     * 
     * return ProveedoresResponse.builder()
     * .id(proveedores.getId())
     * .nombre(proveedores.getNombre())
     * .descripcion(proveedores.getDescripcion())
     * .idCategoria(proveedores.getIdCategoria())
     * .categoria(categoria)
     * .build();
     * 
     * }
     */
}
