package com.upeu.proveedores.controller;

import com.upeu.proveedores.dto.ProveedoresRequest;
import com.upeu.proveedores.dto.ProveedoresResponse;
import com.upeu.proveedores.service.ProveedoresService;
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
@RequestMapping("/api/v1/proveedores")
@RequiredArgsConstructor
public class ProveedoresController {

    private final ProveedoresService proveedoresService;

    @PostMapping
    public ResponseEntity<ProveedoresResponse> create(@Valid @RequestBody ProveedoresRequest request) {
        ProveedoresResponse response = proveedoresService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProveedoresResponse>> findAll() {
        System.out.println("holllaaaa");
        return ResponseEntity.ok(proveedoresService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedoresResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(proveedoresService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedoresResponse> update(@PathVariable Integer id,
                                                   @Valid @RequestBody ProveedoresRequest request) {
        return ResponseEntity.ok(proveedoresService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        proveedoresService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> findDetalleById(@PathVariable Integer id) {
        return ResponseEntity.ok(proveedoresService.findDetalleById(id));
    }

}
