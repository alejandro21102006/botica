package com.upeu.pagos.repository;

import com.upeu.pagos.entity.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagosRepository extends JpaRepository<Pagos, Integer> {
}
