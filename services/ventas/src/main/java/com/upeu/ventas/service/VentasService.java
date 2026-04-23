package com.upeu.ventas.service;

import com.upeu.ventas.dto.VentasRequest;
import com.upeu.ventas.dto.VentasResponse;

import java.util.List;

public interface VentasService {

    VentasResponse create(VentasRequest request);

    List<VentasResponse> findAll();

    VentasResponse findById(Integer id);

    VentasResponse update(Integer id, VentasRequest request);

    void delete(Integer id);

    VentasResponse findDetalleById(Integer id);
}
