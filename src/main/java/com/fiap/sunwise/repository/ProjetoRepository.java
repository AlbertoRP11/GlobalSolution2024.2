package com.fiap.sunwise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fiap.sunwise.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findByUsuarioId(Long usuarioId);
}

