package com.upeu.carrito.service;

import com.upeu.carrito.dto.CarritoRequest;
import com.upeu.carrito.dto.CarritoResponse;
import com.upeu.carrito.dto.CheckoutResponse;

import java.util.List;

public interface CarritoService {

    CarritoResponse create(CarritoRequest request);

    List<CarritoResponse> findAll();

    CarritoResponse findById(Integer id);

    CarritoResponse update(Integer id, CarritoRequest request);

    void delete(Integer id);

    CarritoResponse findDetalleById(Integer id);

    CheckoutResponse checkout(Integer carritoId);
}
