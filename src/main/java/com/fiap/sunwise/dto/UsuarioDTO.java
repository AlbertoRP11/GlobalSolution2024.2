package com.fiap.sunwise.dto;

import com.fiap.sunwise.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String email;
    private String nomeEmpresa;

    public static UsuarioDTO transformaEmDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getEmail(), usuario.getNomeEmpresa());
    }
}
