package com.fiap.sunwise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.sunwise.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByUsuarioId(Long usuarioId);
    Optional<Cliente> findByEmail(String email);
}
