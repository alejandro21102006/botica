package com.upeu.inventario.service;

import com.upeu.inventario.dto.InventarioRequest;
import com.upeu.inventario.dto.InventarioResponse;

import java.util.List;

public interface InventarioService {

    InventarioResponse create(InventarioRequest request);

    List<InventarioResponse> findAll();

    InventarioResponse findById(Integer id);

    InventarioResponse update(Integer id, InventarioRequest request);

    void delete(Integer id);

    InventarioResponse findDetalleById(Integer id);
}
