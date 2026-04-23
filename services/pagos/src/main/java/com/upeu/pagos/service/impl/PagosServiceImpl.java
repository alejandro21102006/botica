package com.upeu.pagos.service.impl;

import com.upeu.pagos.dto.PagosRequest;
import com.upeu.pagos.dto.PagosResponse;
import com.upeu.pagos.entity.Pagos;
import com.upeu.pagos.exception.ResourceNotFoundException;
import com.upeu.pagos.mapper.PagosMapper;
import com.upeu.pagos.repository.PagosRepository;
import com.upeu.pagos.service.PagosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.upeu.pagos.client.CatalogoClient;
import com.upeu.pagos.client.VentasClient;
import com.upeu.pagos.dto.CategoriaDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagosServiceImpl implements PagosService {

    private final PagosRepository pagosRepository;
    private final PagosMapper pagosMapper;
    private final CatalogoClient catalogoClient;
    private final VentasClient ventasClient;

    @Override
    @Transactional
    public PagosResponse create(PagosRequest request) {
        log.info("Iniciando creacion de pagos con nombre: {} y idCategoria: {}", request.getNombre(),
                request.getIdCategoria());
        Pagos pagos = pagosMapper.toEntity(request);
        Pagos savedPagos = pagosRepository.save(pagos);
        log.info("Pagos creado exitosamente con ID: {}", savedPagos.getId());
        return pagosMapper.toResponse(savedPagos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagosResponse> findAll() {
        log.info("Recuperando lista de pagos");
        List<PagosResponse> pagos = pagosRepository.findAll()
                .stream()
                .map(pagosMapper::toResponse)
                .toList();
        log.info("Se encontraron {} pagos", pagos.size());
        return pagos;
    }

    @Override
    @Transactional(readOnly = true)
    public PagosResponse findById(Integer id) {
        log.info("Buscando pagos con ID: {}", id);
        Pagos pagos = getPagosById(id);
        log.info("Pagos encontrado: {} (ID: {})", pagos.getNombre(), id);
        return pagosMapper.toResponse(pagos);
    }

    @Override
    @Transactional
    public PagosResponse update(Integer id, PagosRequest request) {
        log.info("Iniciando actualizacion de pagos ID: {}", id);
        Pagos pagos = getPagosById(id);
        pagosMapper.updateEntityFromRequest(pagos, request);
        Pagos updatedPagos = pagosRepository.save(pagos);
        log.info("Pagos ID: {} actualizado exitosamente", id);
        return pagosMapper.toResponse(updatedPagos);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Iniciando eliminacion de pagos ID: {}", id);
        getPagosById(id);
        pagosRepository.deleteById(id);
        log.info("Pagos ID: {} eliminado exitosamente", id);
    }

    private Pagos getPagosById(Integer id) {
        return pagosRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pagos no encontrado: ID {}", id);
                    return new ResourceNotFoundException("Pagos con id " + id + " no encontrado");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public PagosResponse findDetalleById(Integer id) {
        log.info("Buscando detalle de pagos con ID: {}", id);

        Pagos pagos = getPagosById(id);

        CategoriaDto categoria = null;

        try {
            categoria = catalogoClient.findCategoriaById(
                    pagos.getIdCategoria().longValue());
        } catch (Exception e) {
            log.warn("No se pudo obtener la categoría desde catalogo. idCategoria={}", pagos.getIdCategoria(), e);
        }

        return PagosResponse.builder()
                .id(pagos.getId())
                .nombre(pagos.getNombre())
                .descripcion(pagos.getDescripcion())
                .idCategoria(pagos.getIdCategoria())
                .categoria(categoria)
                .build();
    }

    @Override
    public String confirmarPagoVenta(Integer ventaId) {
        try {
            return ventasClient.confirmarPago(ventaId);
        } catch (Exception e) {
            log.warn("No se pudo confirmar pago para venta {}", ventaId, e);
            return "Pago registrado localmente, pendiente de confirmacion en ventas";
        }
    }
    /*
     * @Override
     * 
     * @Transactional(readOnly = true)
     * public PagosResponse findDetalleById(Integer id) {
     * log.info("Buscando detalle de pagos con ID: {}", id);
     * 
     * Pagos pagos = getPagosById(id);
     * 
     * CategoriaDto categoria = catalogoClient.findCategoriaById(
     * pagos.getIdCategoria().longValue());
     * 
     * return PagosResponse.builder()
     * .id(pagos.getId())
     * .nombre(pagos.getNombre())
     * .descripcion(pagos.getDescripcion())
     * .idCategoria(pagos.getIdCategoria())
     * .categoria(categoria)
     * .build();
     * 
     * }
     */
}
