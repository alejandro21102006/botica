package com.upeu.clientes.service;

import com.upeu.clientes.dto.ClientesRequest;
import com.upeu.clientes.dto.ClientesResponse;

import java.util.List;

public interface ClientesService {

    ClientesResponse create(ClientesRequest request);

    List<ClientesResponse> findAll();

    ClientesResponse findById(Integer id);

    ClientesResponse update(Integer id, ClientesRequest request);

    void delete(Integer id);

    ClientesResponse findDetalleById(Integer id);
}
