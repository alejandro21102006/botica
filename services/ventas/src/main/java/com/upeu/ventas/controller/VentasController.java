package com.upeu.ventas.controller;

import com.upeu.ventas.dto.VentasRequest;
import com.upeu.ventas.dto.VentasResponse;
import com.upeu.ventas.service.VentasService;
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
@RequestMapping("/api/v1/ventas")
@RequiredArgsConstructor
public class VentasController {

    private final VentasService ventasService;

    @PostMapping
    public ResponseEntity<VentasResponse> create(@Valid @RequestBody VentasRequest request) {
        VentasResponse response = ventasService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<VentasResponse>> findAll() {
        System.out.println("holllaaaa");
        return ResponseEntity.ok(ventasService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentasResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(ventasService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentasResponse> update(@PathVariable Integer id,
                                                   @Valid @RequestBody VentasRequest request) {
        return ResponseEntity.ok(ventasService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ventasService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> findDetalleById(@PathVariable Integer id) {
        return ResponseEntity.ok(ventasService.findDetalleById(id));
    }

    @PostMapping("/registrar-desde-carrito/{id}")
    public ResponseEntity<String> registrarDesdeCarrito(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Venta registrada desde carrito " + id);
    }

    @PostMapping("/{id}/confirmar-pago")
    public ResponseEntity<String> confirmarPago(@PathVariable Integer id) {
        return ResponseEntity.ok("Pago confirmado para venta " + id);
    }

}
