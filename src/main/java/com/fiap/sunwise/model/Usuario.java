package com.fiap.sunwise.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "TB_CNW_USUARIOS") 
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String role;
    @NotBlank(message = "{usuario.nome.notblank}")
    @Size(min = 3, message =  "{usuario.nome.size}")
    private String nomeEmpresa;

    @NotBlank(message = "{usuario.email.notblank}")
    @Email
    private String email;

    @NotBlank @Size(min = 6, max = 6, message = "{usuario.senha.size}")
    private String senha;

    

    
}