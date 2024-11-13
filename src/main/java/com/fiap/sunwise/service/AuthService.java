package com.fiap.sunwise.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fiap.sunwise.model.Credentials;
import com.fiap.sunwise.model.Token;
import com.fiap.sunwise.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public Token login(Credentials credentials) {
        var user = usuarioRepository.findByEmail(credentials.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(credentials.password(), user.getSenha())) {
            throw new RuntimeException("Invalid password");
        }

        return tokenService.create(user);

    }

}