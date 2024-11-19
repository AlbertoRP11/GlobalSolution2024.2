package com.fiap.sunwise.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@Table(name = "TB_SNW_USUARIOS")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email @NotBlank(message = "{usuario.email.notblank}")
    private String email;

    @NotBlank @Size(min = 6, max = 6, message = "{usuario.senha.size}")
    private String senha;

    @NotBlank(message = "{usuario.nome.notblank}")
    @Size(min = 3, message =  "{usuario.nome.size}")
    private String nomeEmpresa;

}
