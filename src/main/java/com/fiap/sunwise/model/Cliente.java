package com.fiap.sunwise.model;


import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;import jakarta.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "T_SNW_CLIENTE")
public class Cliente {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    

    @NotBlank(message = "{cliente.nome.notblank}")
    @Size(min = 3, message =  "{cliente.nome.size}")
    private String nome;

    @NotBlank(message = "{cliente.email.notblank}")
    @Email
    private String email;

    @NotBlank(message = "{cliente.endereco.notblank}")
    private String endereco;

    @NotBlank(message = "{cliente.telefone.notblank}")
    private String telefone;

    private Long userId;
}

