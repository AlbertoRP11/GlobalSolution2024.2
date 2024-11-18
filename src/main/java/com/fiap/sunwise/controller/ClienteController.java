package com.fiap.sunwise.controller;

import com.fiap.sunwise.model.Cliente;
import com.fiap.sunwise.model.Projeto;
import com.fiap.sunwise.repository.ClienteRepository;
import com.fiap.sunwise.service.ClienteService;
import com.fiap.sunwise.service.ProjetoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
@Tag(name = "Clientes", description = "Endpoint relacionado com os projetos dos clientes SunWise")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;
     @Autowired
    private ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Cadastra um cliente no sistema.")
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody Cliente cliente) {
        Cliente clienteCriado = clienteRepository.save(cliente);
        return new ResponseEntity<>(clienteCriado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um cliente pelo id.")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Busca todos os Clientes de um usuário.")
    public ResponseEntity<List<Cliente>> getClientesByUsuarioId(@PathVariable Long usuarioId) {
        List<Cliente> clientes = clienteService.getClientesByUsuarioId(usuarioId);
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Lista todos os clientes cadastrados no sistema.")
    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um cliente com base no id.")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente clienteAtualizado) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteAtualizado.setId(id);
        Cliente clienteSalvo = clienteRepository.save(clienteAtualizado);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga um cliente do sistema.")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
