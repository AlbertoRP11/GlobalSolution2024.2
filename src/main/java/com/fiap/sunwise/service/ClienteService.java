package com.fiap.sunwise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.sunwise.model.Cliente;
import com.fiap.sunwise.repository.ClienteRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EntityManager entityManager;

    public List<Cliente> getClientesByUsuarioId(Long usuarioId) {
        return clienteRepository.findByUsuarioId(usuarioId);
    }

    public boolean clienteExiste(Long id) {
        return entityManager.createQuery("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.id = :id", Boolean.class)
                .setParameter("id", id)
                .getSingleResult();
    }
    @Transactional
    public void inserirCliente(String nome, String email, String endereco, String telefone, Long userId) {
        entityManager.createNativeQuery("BEGIN pkg_cliente.inserir_cliente(NULL, :nome, :email, :endereco, :telefone, :userId); END;")
                .setParameter("nome", nome)
                .setParameter("email", email)
                .setParameter("endereco", endereco)
                .setParameter("telefone", telefone)
                .setParameter("userId", userId) // O último parâmetro corresponde a p_user_id
                .executeUpdate();
    }

    @Transactional
    public void atualizarCliente(Long id, String nome, String email, String endereco, String telefone) {
        entityManager.createNativeQuery("BEGIN pkg_cliente.atualizar_cliente(:id, :nome, :email, :endereco, :telefone); END;")
                .setParameter("id", id)
                .setParameter("nome", nome)
                .setParameter("email", email)
                .setParameter("endereco", endereco)
                .setParameter("telefone", telefone)
                .executeUpdate();
    }

    @Transactional
    public void deletarCliente(Long id) {
        entityManager.createNativeQuery("BEGIN pkg_cliente.deletar_cliente(:id); END;")
                .setParameter("id", id)
                .executeUpdate();
    }
}
