package com.upeu.auth.service;

import com.upeu.auth.dto.AuthRequest;
import com.upeu.auth.dto.AuthResponse;

import java.util.List;

public interface AuthService {

    AuthResponse create(AuthRequest request);

    List<AuthResponse> findAll();

    AuthResponse findById(Integer id);

    AuthResponse update(Integer id, AuthRequest request);

    void delete(Integer id);

    AuthResponse findDetalleById(Integer id);
}
