package com.upeu.ventas.repository;

import com.upeu.ventas.entity.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentasRepository extends JpaRepository<Ventas, Integer> {
}
