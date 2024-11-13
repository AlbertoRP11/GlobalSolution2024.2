package com.fiap.sunwise.controller;

import com.fiap.sunwise.model.Usuario;
import com.fiap.sunwise.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("user")
@Slf4j
@Tag(name = "usuarios", description = "Endpoint relacionado com os usuários SunWise")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Lista todos os usuários cadastrados no sistema.",
            description = "Endpoint que retorna um array de objetos do tipo usuário")
    public List<Usuario> index(){
        return usuarioRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Cadastra um usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação do usuário"),
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    })
    public Usuario create(@RequestBody @Valid Usuario usuario) {
        log.info("cadastrando Usuario: {}", usuario);
        return usuarioRepository.save(usuario);
    }

}
