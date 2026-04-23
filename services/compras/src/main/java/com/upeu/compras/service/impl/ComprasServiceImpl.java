package com.upeu.compras.service.impl;

import com.upeu.compras.dto.ComprasRequest;
import com.upeu.compras.dto.ComprasResponse;
import com.upeu.compras.entity.Compras;
import com.upeu.compras.exception.ResourceNotFoundException;
import com.upeu.compras.mapper.ComprasMapper;
import com.upeu.compras.repository.ComprasRepository;
import com.upeu.compras.service.ComprasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.upeu.compras.client.CatalogoClient;
import com.upeu.compras.client.InventarioClient;
import com.upeu.compras.dto.CategoriaDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComprasServiceImpl implements ComprasService {

    private final ComprasRepository comprasRepository;
    private final ComprasMapper comprasMapper;
    private final CatalogoClient catalogoClient;
    private final InventarioClient inventarioClient;

    @Override
    @Transactional
    public ComprasResponse create(ComprasRequest request) {
        log.info("Iniciando creacion de compras con nombre: {} y idCategoria: {}", request.getNombre(),
                request.getIdCategoria());
        Compras compras = comprasMapper.toEntity(request);
        Compras savedCompras = comprasRepository.save(compras);
        log.info("Compras creado exitosamente con ID: {}", savedCompras.getId());
        return comprasMapper.toResponse(savedCompras);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComprasResponse> findAll() {
        log.info("Recuperando lista de compras");
        List<ComprasResponse> compras = comprasRepository.findAll()
                .stream()
                .map(comprasMapper::toResponse)
                .toList();
        log.info("Se encontraron {} compras", compras.size());
        return compras;
    }

    @Override
    @Transactional(readOnly = true)
    public ComprasResponse findById(Integer id) {
        log.info("Buscando compras con ID: {}", id);
        Compras compras = getComprasById(id);
        log.info("Compras encontrado: {} (ID: {})", compras.getNombre(), id);
        return comprasMapper.toResponse(compras);
    }

    @Override
    @Transactional
    public ComprasResponse update(Integer id, ComprasRequest request) {
        log.info("Iniciando actualizacion de compras ID: {}", id);
        Compras compras = getComprasById(id);
        comprasMapper.updateEntityFromRequest(compras, request);
        Compras updatedCompras = comprasRepository.save(compras);
        log.info("Compras ID: {} actualizado exitosamente", id);
        return comprasMapper.toResponse(updatedCompras);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Iniciando eliminacion de compras ID: {}", id);
        getComprasById(id);
        comprasRepository.deleteById(id);
        log.info("Compras ID: {} eliminado exitosamente", id);
    }

    private Compras getComprasById(Integer id) {
        return comprasRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Compras no encontrado: ID {}", id);
                    return new ResourceNotFoundException("Compras con id " + id + " no encontrado");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public ComprasResponse findDetalleById(Integer id) {
        log.info("Buscando detalle de compras con ID: {}", id);

        Compras compras = getComprasById(id);

        CategoriaDto categoria = null;

        try {
            categoria = catalogoClient.findCategoriaById(
                    compras.getIdCategoria().longValue());
        } catch (Exception e) {
            log.warn("No se pudo obtener la categoría desde catalogo. idCategoria={}", compras.getIdCategoria(), e);
        }

        return ComprasResponse.builder()
                .id(compras.getId())
                .nombre(compras.getNombre())
                .descripcion(compras.getDescripcion())
                .idCategoria(compras.getIdCategoria())
                .categoria(categoria)
                .build();
    }

    @Override
    public String registrarIngresoStock(Integer compraId) {
        try {
            return inventarioClient.ingresar(compraId);
        } catch (Exception e) {
            log.warn("No se pudo registrar ingreso de stock para compra {}", compraId, e);
            return "Compra registrada localmente, pendiente de sincronizacion en inventario";
        }
    }
    /*
     * @Override
     * 
     * @Transactional(readOnly = true)
     * public ComprasResponse findDetalleById(Integer id) {
     * log.info("Buscando detalle de compras con ID: {}", id);
     * 
     * Compras compras = getComprasById(id);
     * 
     * CategoriaDto categoria = catalogoClient.findCategoriaById(
     * compras.getIdCategoria().longValue());
     * 
     * return ComprasResponse.builder()
     * .id(compras.getId())
     * .nombre(compras.getNombre())
     * .descripcion(compras.getDescripcion())
     * .idCategoria(compras.getIdCategoria())
     * .categoria(categoria)
     * .build();
     * 
     * }
     */
}
