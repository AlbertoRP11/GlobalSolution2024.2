package com.fiap.sunwise.controller;

import com.fiap.sunwise.model.Cliente;
import com.fiap.sunwise.repository.ClienteRepository;
import com.fiap.sunwise.service.ClienteService;

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

    @PostMapping
    @Operation(summary = "Cadastra um cliente no sistema.")
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody Cliente cliente) {
        clienteService.inserirCliente(cliente.getNome(), cliente.getEmail(), cliente.getEndereco(), cliente.getTelefone(), cliente.getUsuarioId());
        Cliente clienteCriado = clienteRepository.findByEmail(cliente.getEmail()).orElse(null);
        if (clienteCriado == null) {
            throw new RuntimeException("Erro ao buscar o cliente recém-criado.");
        }
    
        return new ResponseEntity<>(clienteCriado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um cliente com base no id.")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente clienteAtualizado) {
        if (!clienteService.clienteExiste(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteService.atualizarCliente(id, clienteAtualizado.getNome(), clienteAtualizado.getEmail(), clienteAtualizado.getEndereco(), clienteAtualizado.getTelefone());
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga um cliente do sistema.")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        if (!clienteService.clienteExiste(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
