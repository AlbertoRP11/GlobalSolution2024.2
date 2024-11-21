package com.fiap.sunwise.service;

import com.fiap.sunwise.model.Usuario;
import com.fiap.sunwise.repository.UsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticarUsuario(String email, String senha) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);

        if (optionalUsuario.isPresent() && optionalUsuario.get().getSenha().equals(senha)) {
            return optionalUsuario.get();
        }
        return null;
    }

    @Transactional
    public void inserirUsuario(String nomeEmpresa, String email, String senha) {
        entityManager.createNativeQuery("BEGIN pkg_usuario.inserir_usuario(NULL, :nomeEmpresa, :email, :senha); END;")
                .setParameter("nomeEmpresa", nomeEmpresa)
                .setParameter("email", email)
                .setParameter("senha", senha)
                .executeUpdate();
    }
    
    @Transactional
    public void atualizarUsuario(Long id, String nomeEmpresa, String email, String senha) {
        entityManager.createNativeQuery("BEGIN pkg_usuario.atualizar_usuario(:id, :nomeEmpresa, :email, :senha); END;")
                .setParameter("id", id)
                .setParameter("nomeEmpresa", nomeEmpresa)
                .setParameter("email", email)
                .setParameter("senha", senha)
                .executeUpdate();
    }

    @Transactional
    public void deletarUsuario(Long id) {
        entityManager.createNativeQuery("BEGIN pkg_usuario.deletar_usuario(:id); END;")
                .setParameter("id", id)
                .executeUpdate();
    }

}
