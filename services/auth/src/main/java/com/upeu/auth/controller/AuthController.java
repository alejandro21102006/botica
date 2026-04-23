package com.upeu.auth.controller;

import com.upeu.auth.dto.AuthRequest;
import com.upeu.auth.dto.AuthResponse;
import com.upeu.auth.service.AuthService;
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
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthResponse> create(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AuthResponse>> findAll() {
        System.out.println("holllaaaa");
        return ResponseEntity.ok(authService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(authService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthResponse> update(@PathVariable Integer id,
                                                   @Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        authService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> findDetalleById(@PathVariable Integer id) {
        return ResponseEntity.ok(authService.findDetalleById(id));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("token-demo-admin");
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh() {
        return ResponseEntity.ok("token-demo-refresh");
    }

    @GetMapping("/usuarios/me")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok("usuario-demo rol=ADMIN");
    }

}
