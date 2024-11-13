package com.fiap.sunwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.sunwise.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
