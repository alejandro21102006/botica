package com.upeu.proveedores.service;

import com.upeu.proveedores.dto.ProveedoresRequest;
import com.upeu.proveedores.dto.ProveedoresResponse;

import java.util.List;

public interface ProveedoresService {

    ProveedoresResponse create(ProveedoresRequest request);

    List<ProveedoresResponse> findAll();

    ProveedoresResponse findById(Integer id);

    ProveedoresResponse update(Integer id, ProveedoresRequest request);

    void delete(Integer id);

    ProveedoresResponse findDetalleById(Integer id);
}
