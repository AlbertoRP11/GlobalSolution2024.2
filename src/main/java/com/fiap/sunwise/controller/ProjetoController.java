package com.fiap.sunwise.controller;

import com.fiap.sunwise.model.Projeto;
import com.fiap.sunwise.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping
    @Operation(summary = "Cadastra um projeto no sistema.")
    public ResponseEntity<Projeto> createProjeto(@RequestBody Projeto projeto) {
        Projeto savedProjeto = projetoService.saveProjeto(projeto);
        return new ResponseEntity<>(savedProjeto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um projeto pelo id.")
    public ResponseEntity<Projeto> getProjetoById(@PathVariable Long id) {
        Projeto projeto = projetoService.getProjetoById(id);
        if (projeto != null) {
            return new ResponseEntity<>(projeto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @Operation(summary = "Lista todos os projetos cadastrados no sistema.")
    public ResponseEntity<List<Projeto>> getAllProjetos() {
        List<Projeto> projetos = projetoService.getAllProjetos();
        return new ResponseEntity<>(projetos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um projeto com base no id.")
    public ResponseEntity<Projeto> updateProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
        Projeto updatedProjeto = projetoService.updateProjeto(id, projeto);
        if (updatedProjeto != null) {
            return new ResponseEntity<>(updatedProjeto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga um projeto do sistema.")
    public ResponseEntity<Void> deleteProjeto(@PathVariable Long id) {
        if (projetoService.deleteProjeto(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
