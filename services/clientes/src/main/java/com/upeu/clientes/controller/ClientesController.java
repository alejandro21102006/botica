package com.upeu.clientes.controller;

import com.upeu.clientes.dto.ClientesRequest;
import com.upeu.clientes.dto.ClientesResponse;
import com.upeu.clientes.service.ClientesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClientesController {

    private final ClientesService clientesService;

    @PostMapping
    public ResponseEntity<ClientesResponse> create(@Valid @RequestBody ClientesRequest request) {
        ClientesResponse response = clientesService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClientesResponse>> findAll() {
        System.out.println("holllaaaa");
        return ResponseEntity.ok(clientesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientesResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(clientesService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientesResponse> update(@PathVariable Integer id,
                                                   @Valid @RequestBody ClientesRequest request) {
        return ResponseEntity.ok(clientesService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clientesService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> findDetalleById(@PathVariable Integer id) {
        return ResponseEntity.ok(clientesService.findDetalleById(id));
    }

}
