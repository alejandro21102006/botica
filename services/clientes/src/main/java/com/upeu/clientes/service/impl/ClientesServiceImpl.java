package com.upeu.clientes.service.impl;

import com.upeu.clientes.dto.ClientesRequest;
import com.upeu.clientes.dto.ClientesResponse;
import com.upeu.clientes.entity.Clientes;
import com.upeu.clientes.exception.ResourceNotFoundException;
import com.upeu.clientes.mapper.ClientesMapper;
import com.upeu.clientes.repository.ClientesRepository;
import com.upeu.clientes.service.ClientesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.upeu.clientes.client.CatalogoClient;
import com.upeu.clientes.dto.CategoriaDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientesServiceImpl implements ClientesService {

    private final ClientesRepository clientesRepository;
    private final ClientesMapper clientesMapper;
    private final CatalogoClient catalogoClient;

    @Override
    @Transactional
    public ClientesResponse create(ClientesRequest request) {
        log.info("Iniciando creacion de clientes con nombre: {} y idCategoria: {}", request.getNombre(),
                request.getIdCategoria());
        Clientes clientes = clientesMapper.toEntity(request);
        Clientes savedClientes = clientesRepository.save(clientes);
        log.info("Clientes creado exitosamente con ID: {}", savedClientes.getId());
        return clientesMapper.toResponse(savedClientes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientesResponse> findAll() {
        log.info("Recuperando lista de clientes");
        List<ClientesResponse> clientes = clientesRepository.findAll()
                .stream()
                .map(clientesMapper::toResponse)
                .toList();
        log.info("Se encontraron {} clientes", clientes.size());
        return clientes;
    }

    @Override
    @Transactional(readOnly = true)
    public ClientesResponse findById(Integer id) {
        log.info("Buscando clientes con ID: {}", id);
        Clientes clientes = getClientesById(id);
        log.info("Clientes encontrado: {} (ID: {})", clientes.getNombre(), id);
        return clientesMapper.toResponse(clientes);
    }

    @Override
    @Transactional
    public ClientesResponse update(Integer id, ClientesRequest request) {
        log.info("Iniciando actualizacion de clientes ID: {}", id);
        Clientes clientes = getClientesById(id);
        clientesMapper.updateEntityFromRequest(clientes, request);
        Clientes updatedClientes = clientesRepository.save(clientes);
        log.info("Clientes ID: {} actualizado exitosamente", id);
        return clientesMapper.toResponse(updatedClientes);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("Iniciando eliminacion de clientes ID: {}", id);
        getClientesById(id);
        clientesRepository.deleteById(id);
        log.info("Clientes ID: {} eliminado exitosamente", id);
    }

    private Clientes getClientesById(Integer id) {
        return clientesRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Clientes no encontrado: ID {}", id);
                    return new ResourceNotFoundException("Clientes con id " + id + " no encontrado");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public ClientesResponse findDetalleById(Integer id) {
        log.info("Buscando detalle de clientes con ID: {}", id);

        Clientes clientes = getClientesById(id);

        CategoriaDto categoria = null;

        try {
            categoria = catalogoClient.findCategoriaById(
                    clientes.getIdCategoria().longValue());
        } catch (Exception e) {
            log.warn("No se pudo obtener la categoría desde catalogo. idCategoria={}", clientes.getIdCategoria(), e);
        }

        return ClientesResponse.builder()
                .id(clientes.getId())
                .nombre(clientes.getNombre())
                .descripcion(clientes.getDescripcion())
                .idCategoria(clientes.getIdCategoria())
                .categoria(categoria)
                .build();
    }
    /*
     * @Override
     * 
     * @Transactional(readOnly = true)
     * public ClientesResponse findDetalleById(Integer id) {
     * log.info("Buscando detalle de clientes con ID: {}", id);
     * 
     * Clientes clientes = getClientesById(id);
     * 
     * CategoriaDto categoria = catalogoClient.findCategoriaById(
     * clientes.getIdCategoria().longValue());
     * 
     * return ClientesResponse.builder()
     * .id(clientes.getId())
     * .nombre(clientes.getNombre())
     * .descripcion(clientes.getDescripcion())
     * .idCategoria(clientes.getIdCategoria())
     * .categoria(categoria)
     * .build();
     * 
     * }
     */
}
