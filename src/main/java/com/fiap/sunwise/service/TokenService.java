package com.fiap.sunwise.service;

import com.fiap.sunwise.model.Token;
import com.fiap.sunwise.model.Usuario;
import com.fiap.sunwise.repository.UsuarioRepository;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public Algorithm ALGORITHM;
    private final UsuarioRepository usuarioRepository;

    public TokenService(@Value("${jwt.secret}") String secret, UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        ALGORITHM = Algorithm.HMAC256(secret);
    }

    public Token create(Usuario user){
        var expires = LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.ofHours(-3));

        var token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole())
                .withExpiresAt(expires)
                .sign(ALGORITHM);

        return new Token(token, user.getEmail(), user.getId().toString(), user.getRole());
    }

    public Usuario getUserFromToken(String token) {
        var userId =JWT.require(ALGORITHM)
                .build()
                .verify(token)
                .getSubject();


        return usuarioRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

}