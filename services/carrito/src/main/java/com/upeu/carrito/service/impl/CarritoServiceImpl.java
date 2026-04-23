package com.upeu.carrito.service.impl;

import com.upeu.carrito.dto.CarritoRequest;
import com.upeu.carrito.dto.CarritoResponse;
import com.upeu.carrito.dto.CheckoutResponse;
import com.upeu.carrito.entity.Carrito;
import com.upeu.carrito.exception.ResourceNotFoundException;
import com.upeu.carrito.mapper.CarritoMapper;
import com.upeu.carrito.repository.CarritoRepository;
import com.upeu.carrito.service.CarritoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.upeu.carrito.client.CatalogoClient;
import com.upeu.carrito.client.InventarioClient;
import com.upeu.carrito.client.VentasClient;
import com.upeu.carrito.dto.CategoriaDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoMapper carritoMapper;
    private final CatalogoClient catalogoClient;
    private final InventarioClient inventarioClient;
    private final VentasClient ventasClient;

    @Override
    @Transactional
    public CarritoResponse create(CarritoRequest request) {
        log.info("Iniciando creacion de carrito con nombre: {} y idCategoria: {}", request.getNombre(),
                request.getIdCategoria());
        Carrito carrito = carritoMapper.toEntity(request);
        Carrito savedCarrito = carritoRepository.save(carrito);
        log.info("Carrito creado exitosamente con ID: {}", savedCarrito.getId());
        return carritoMapper.toResponse(savedCarrito);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarritoResponse> findAll() {
        log.info("Recuperando lista de carritos");
        List<CarritoResponse> carritos = carritoRepository.findAll()
                .stream()
                .map(carritoMapper::toResponse)
                .toList();
        log.info("Se encontraron {} carritos", carritos.size());
        return carritos;
    }

    @Override
    @Transactional(readOnly = true)
    public CarritoResponse findById(Integer id) {
        log.info("Buscando carrito con ID: {}", id);
        Carrito carrito = getCarritoById(id);
        log.info("Carrito encontrado: {} (ID: {})", carrito.getNombre(), id);
        return carritoMapper.toResponse(carrito);
    }

    @Override
    @Transactional
    public CarritoResponse update(Integer id, CarritoRequest request) {
        log.info("Iniciando actualizacion de carrito ID: {}", id);
        Carrito carrito = getCarritoById(id);
        carritoMapper.updateEntityFromRequest(carrito, request);
        Carrito updatedCarrito = carritoRepository.save(carrito);
        log.info("Carrito ID: {} actualizado exitosamente", id);
        return carritoMapper.toResponse(updatedCarrito);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Iniciando eliminacion de carrito ID: {}", id);
        getCarritoById(id);
        carritoRepository.deleteById(id);
        log.info("Carrito ID: {} eliminado exitosamente", id);
    }

    private Carrito getCarritoById(Integer id) {
        return carritoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Carrito no encontrado: ID {}", id);
                    return new ResourceNotFoundException("Carrito con id " + id + " no encontrado");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public CarritoResponse findDetalleById(Integer id) {
        log.info("Buscando detalle de carrito con ID: {}", id);

        Carrito carrito = getCarritoById(id);

        CategoriaDto categoria = null;

        try {
            categoria = catalogoClient.findCategoriaById(
                    carrito.getIdCategoria().longValue());
        } catch (Exception e) {
            log.warn("No se pudo obtener la categoría desde catalogo. idCategoria={}", carrito.getIdCategoria(), e);
        }

        return CarritoResponse.builder()
                .id(carrito.getId())
                .nombre(carrito.getNombre())
                .descripcion(carrito.getDescripcion())
                .idCategoria(carrito.getIdCategoria())
                .categoria(categoria)
                .build();
    }

    @Override
    @Transactional
    public CheckoutResponse checkout(Integer carritoId) {
        Carrito carrito = getCarritoById(carritoId);
        boolean inventarioReservado = false;
        boolean ventaGenerada = false;

        try {
            inventarioClient.reservar(carrito.getId());
            inventarioReservado = true;
        } catch (Exception e) {
            log.warn("No se pudo reservar inventario para carrito {}", carritoId, e);
        }

        if (inventarioReservado) {
            try {
                ventasClient.registrarDesdeCarrito(carrito.getId());
                ventaGenerada = true;
            } catch (Exception e) {
                log.warn("No se pudo generar venta desde carrito {}", carritoId, e);
            }
        }

        String mensaje = ventaGenerada
                ? "Checkout procesado correctamente"
                : "Checkout parcial. Revisar inventario/ventas";

        return CheckoutResponse.builder()
                .carritoId(carritoId)
                .inventarioReservado(inventarioReservado)
                .ventaGenerada(ventaGenerada)
                .mensaje(mensaje)
                .build();
    }
    /*
     * @Override
     * 
     * @Transactional(readOnly = true)
     * public CarritoResponse findDetalleById(Integer id) {
     * log.info("Buscando detalle de carrito con ID: {}", id);
     * 
     * Carrito carrito = getCarritoById(id);
     * 
     * CategoriaDto categoria = catalogoClient.findCategoriaById(
     * carrito.getIdCategoria().longValue());
     * 
     * return CarritoResponse.builder()
     * .id(carrito.getId())
     * .nombre(carrito.getNombre())
     * .descripcion(carrito.getDescripcion())
     * .idCategoria(carrito.getIdCategoria())
     * .categoria(categoria)
     * .build();
     * 
     * }
     */
}
