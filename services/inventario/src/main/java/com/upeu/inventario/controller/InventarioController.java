package com.upeu.inventario.controller;

import com.upeu.inventario.dto.InventarioRequest;
import com.upeu.inventario.dto.InventarioResponse;
import com.upeu.inventario.service.InventarioService;
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
@RequestMapping("/api/v1/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @PostMapping
    public ResponseEntity<InventarioResponse> create(@Valid @RequestBody InventarioRequest request) {
        InventarioResponse response = inventarioService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<InventarioResponse>> findAll() {
        System.out.println("holllaaaa");
        return ResponseEntity.ok(inventarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioResponse> update(@PathVariable Integer id,
                                                   @Valid @RequestBody InventarioRequest request) {
        return ResponseEntity.ok(inventarioService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> findDetalleById(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.findDetalleById(id));
    }

    @PostMapping("/{id}/reservar")
    public ResponseEntity<String> reservar(@PathVariable Integer id) {
        return ResponseEntity.ok("Inventario reservado para carrito/item " + id);
    }

    @PostMapping("/{id}/ingresar")
    public ResponseEntity<String> ingresar(@PathVariable Integer id) {
        return ResponseEntity.ok("Ingreso de stock aplicado para compra " + id);
    }

}
