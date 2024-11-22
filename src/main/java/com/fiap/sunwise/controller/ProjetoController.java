package com.fiap.sunwise.controller;

import com.fiap.sunwise.model.Projeto;
import com.fiap.sunwise.repository.UsuarioRepository;
import com.fiap.sunwise.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projetos", description = "Endpoint relacionado com os projetos dos usuários SunWise")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/{id}")
    @Operation(summary = "Busca um projeto pelo id.")
    public ResponseEntity<Projeto> getProjetoById(@PathVariable Long id) {
        Projeto projeto = projetoService.getProjetoById(id);
        if (projeto != null) {
            return new ResponseEntity<>(projeto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Busca todos os projetos de um usuário.")
    public ResponseEntity<List<Projeto>> getProjetosByUserId(@PathVariable Long UserId) {
        List<Projeto> projetos = projetoService.getProjetosByUserId(UserId);
        if (projetos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projetos, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Lista todos os projetos cadastrados no sistema.")
    public ResponseEntity<List<Projeto>> getAllProjetos() {
        List<Projeto> projetos = projetoService.getAllProjetos();
        return new ResponseEntity<>(projetos, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Cadastra um projeto no sistema.")
    public ResponseEntity<Projeto> criarProjeto(@Valid @RequestBody Projeto projeto) {
        Projeto projetoCriado = projetoService.inserirProjeto(projeto);

        if (projetoCriado == null) {
            throw new RuntimeException("Erro ao buscar o projeto recém-criado.");
        }

        return new ResponseEntity<>(projetoCriado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um projeto com base no id.")
    public ResponseEntity<Projeto> updateProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
        Projeto updatedProjeto = projetoService.atualizarProjeto(id, projeto);
        if (updatedProjeto != null) {
            return new ResponseEntity<>(updatedProjeto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga um projeto do sistema.")
    public ResponseEntity<Void> deleteProjeto(@PathVariable Long id) {
        if (projetoService.deletarProjeto(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
