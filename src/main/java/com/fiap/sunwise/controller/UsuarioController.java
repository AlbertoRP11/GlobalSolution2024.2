package com.fiap.sunwise.controller;

import com.fiap.sunwise.dto.UsuarioDTO;
import com.fiap.sunwise.model.Usuario;
import com.fiap.sunwise.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "Usuários", description = "Endpoint relacionado com os usuários SunWise")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Cadastra um usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação do usuário"),
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    })
    public UsuarioDTO create(@RequestBody @Valid Usuario usuario) {
        usuarioRepository.save(usuario);
        return UsuarioDTO.transformaEmDTO(usuario);
    }

    @GetMapping("{id}")
    @Operation(summary = "Busca um usuário pelo id.")
    public ResponseEntity<UsuarioDTO> get(@PathVariable Long id) {

        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(UsuarioDTO.transformaEmDTO(usuario));
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuários cadastrados no sistema.")
    public List<UsuarioDTO> index(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(UsuarioDTO::transformaEmDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza os dados de um usuário com base no id.")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @RequestBody Usuario u) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        usuario.setEmail(u.getEmail());
        usuario.setSenha(u.getSenha());
        usuarioRepository.save(usuario);

        System.out.println(usuario);
        return ResponseEntity.ok(UsuarioDTO.transformaEmDTO(usuario));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Apaga um usuário do sistema.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        usuarioRepository.delete(usuario);

        return ResponseEntity.noContent().build();
    }
}
