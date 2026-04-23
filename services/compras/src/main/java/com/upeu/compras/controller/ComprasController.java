package com.upeu.compras.controller;

import com.upeu.compras.dto.ComprasRequest;
import com.upeu.compras.dto.ComprasResponse;
import com.upeu.compras.service.ComprasService;
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
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
public class ComprasController {

    private final ComprasService comprasService;

    @PostMapping
    public ResponseEntity<ComprasResponse> create(@Valid @RequestBody ComprasRequest request) {
        ComprasResponse response = comprasService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ComprasResponse>> findAll() {
        System.out.println("holllaaaa");
        return ResponseEntity.ok(comprasService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComprasResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(comprasService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComprasResponse> update(@PathVariable Integer id,
                                                   @Valid @RequestBody ComprasRequest request) {
        return ResponseEntity.ok(comprasService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        comprasService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> findDetalleById(@PathVariable Integer id) {
        return ResponseEntity.ok(comprasService.findDetalleById(id));
    }

    @PostMapping("/{id}/ingresar-stock")
    public ResponseEntity<String> ingresarStock(@PathVariable Integer id) {
        return ResponseEntity.ok(comprasService.registrarIngresoStock(id));
    }

}
