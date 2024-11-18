package com.fiap.sunwise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.sunwise.model.Cliente;
import com.fiap.sunwise.model.Projeto;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByUsuarioId(Long usuarioId);
}
