package com.upeu.compras.service;

import com.upeu.compras.dto.ComprasRequest;
import com.upeu.compras.dto.ComprasResponse;

import java.util.List;

public interface ComprasService {

    ComprasResponse create(ComprasRequest request);

    List<ComprasResponse> findAll();

    ComprasResponse findById(Integer id);

    ComprasResponse update(Integer id, ComprasRequest request);

    void delete(Integer id);

    ComprasResponse findDetalleById(Integer id);

    String registrarIngresoStock(Integer compraId);
}
