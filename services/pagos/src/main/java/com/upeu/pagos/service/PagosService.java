package com.upeu.pagos.service;

import com.upeu.pagos.dto.PagosRequest;
import com.upeu.pagos.dto.PagosResponse;

import java.util.List;

public interface PagosService {

    PagosResponse create(PagosRequest request);

    List<PagosResponse> findAll();

    PagosResponse findById(Integer id);

    PagosResponse update(Integer id, PagosRequest request);

    void delete(Integer id);

    PagosResponse findDetalleById(Integer id);

    String confirmarPagoVenta(Integer ventaId);
}
