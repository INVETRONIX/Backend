package com.invetronix.backend.APIcompras.repository;

import com.invetronix.backend.APIcompras.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
} 