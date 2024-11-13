package com.fiap.sunwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.sunwise.model.Projeto;
import com.fiap.sunwise.repository.ProjetoRepository;

import java.util.List;


@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public Projeto criarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public List<Projeto> buscarCampanhasPorUsuarioId(Long usuarioId) {
        return projetoRepository.findByUsuarioId(usuarioId);
    }
}