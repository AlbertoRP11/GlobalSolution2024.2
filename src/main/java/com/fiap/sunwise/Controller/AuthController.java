package com.fiap.sunwise.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.sunwise.model.Credentials;
import com.fiap.sunwise.model.Token;
import com.fiap.sunwise.service.AuthService;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials){
        return authService.login(credentials);
    }

}
