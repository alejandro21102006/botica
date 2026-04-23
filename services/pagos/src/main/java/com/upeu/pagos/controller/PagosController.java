package com.upeu.pagos.controller;

import com.upeu.pagos.dto.PagosRequest;
import com.upeu.pagos.dto.PagosResponse;
import com.upeu.pagos.service.PagosService;
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
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
public class PagosController {

    private final PagosService pagosService;

    @PostMapping
    public ResponseEntity<PagosResponse> create(@Valid @RequestBody PagosRequest request) {
        PagosResponse response = pagosService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PagosResponse>> findAll() {
        System.out.println("holllaaaa");
        return ResponseEntity.ok(pagosService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagosResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(pagosService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagosResponse> update(@PathVariable Integer id,
                                                   @Valid @RequestBody PagosRequest request) {
        return ResponseEntity.ok(pagosService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pagosService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> findDetalleById(@PathVariable Integer id) {
        return ResponseEntity.ok(pagosService.findDetalleById(id));
    }

    @PostMapping("/{ventaId}/confirmar")
    public ResponseEntity<String> confirmar(@PathVariable Integer ventaId) {
        return ResponseEntity.ok(pagosService.confirmarPagoVenta(ventaId));
    }

}
