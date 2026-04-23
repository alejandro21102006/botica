package com.upeu.ventas.service.impl;

import com.upeu.ventas.dto.VentasRequest;
import com.upeu.ventas.dto.VentasResponse;
import com.upeu.ventas.entity.Ventas;
import com.upeu.ventas.exception.ResourceNotFoundException;
import com.upeu.ventas.mapper.VentasMapper;
import com.upeu.ventas.repository.VentasRepository;
import com.upeu.ventas.service.VentasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.upeu.ventas.client.CatalogoClient;
import com.upeu.ventas.dto.CategoriaDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentasServiceImpl implements VentasService {

    private final VentasRepository ventasRepository;
    private final VentasMapper ventasMapper;
    private final CatalogoClient catalogoClient;

    @Override
    @Transactional
    public VentasResponse create(VentasRequest request) {
        log.info("Iniciando creacion de ventas con nombre: {} y idCategoria: {}", request.getNombre(),
                request.getIdCategoria());
        Ventas ventas = ventasMapper.toEntity(request);
        Ventas savedVentas = ventasRepository.save(ventas);
        log.info("Ventas creado exitosamente con ID: {}", savedVentas.getId());
        return ventasMapper.toResponse(savedVentas);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentasResponse> findAll() {
        log.info("Recuperando lista de ventas");
        List<VentasResponse> ventas = ventasRepository.findAll()
                .stream()
                .map(ventasMapper::toResponse)
                .toList();
        log.info("Se encontraron {} ventas", ventas.size());
        return ventas;
    }

    @Override
    @Transactional(readOnly = true)
    public VentasResponse findById(Integer id) {
        log.info("Buscando ventas con ID: {}", id);
        Ventas ventas = getVentasById(id);
        log.info("Ventas encontrado: {} (ID: {})", ventas.getNombre(), id);
        return ventasMapper.toResponse(ventas);
    }

    @Override
    @Transactional
    public VentasResponse update(Integer id, VentasRequest request) {
        log.info("Iniciando actualizacion de ventas ID: {}", id);
        Ventas ventas = getVentasById(id);
        ventasMapper.updateEntityFromRequest(ventas, request);
        Ventas updatedVentas = ventasRepository.save(ventas);
        log.info("Ventas ID: {} actualizado exitosamente", id);
        return ventasMapper.toResponse(updatedVentas);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Iniciando eliminacion de ventas ID: {}", id);
        getVentasById(id);
        ventasRepository.deleteById(id);
        log.info("Ventas ID: {} eliminado exitosamente", id);
    }

    private Ventas getVentasById(Integer id) {
        return ventasRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Ventas no encontrado: ID {}", id);
                    return new ResourceNotFoundException("Ventas con id " + id + " no encontrado");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public VentasResponse findDetalleById(Integer id) {
        log.info("Buscando detalle de ventas con ID: {}", id);

        Ventas ventas = getVentasById(id);

        CategoriaDto categoria = null;

        try {
            categoria = catalogoClient.findCategoriaById(
                    ventas.getIdCategoria().longValue());
        } catch (Exception e) {
            log.warn("No se pudo obtener la categoría desde catalogo. idCategoria={}", ventas.getIdCategoria(), e);
        }

        return VentasResponse.builder()
                .id(ventas.getId())
                .nombre(ventas.getNombre())
                .descripcion(ventas.getDescripcion())
                .idCategoria(ventas.getIdCategoria())
                .categoria(categoria)
                .build();
    }
    /*
     * @Override
     * 
     * @Transactional(readOnly = true)
     * public VentasResponse findDetalleById(Integer id) {
     * log.info("Buscando detalle de ventas con ID: {}", id);
     * 
     * Ventas ventas = getVentasById(id);
     * 
     * CategoriaDto categoria = catalogoClient.findCategoriaById(
     * ventas.getIdCategoria().longValue());
     * 
     * return VentasResponse.builder()
     * .id(ventas.getId())
     * .nombre(ventas.getNombre())
     * .descripcion(ventas.getDescripcion())
     * .idCategoria(ventas.getIdCategoria())
     * .categoria(categoria)
     * .build();
     * 
     * }
     */
}
