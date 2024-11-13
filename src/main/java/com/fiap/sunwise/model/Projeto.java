package com.fiap.sunwise.model;

import jakarta.persistence.OneToMany;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "TB_SNW_PROJETOS")
public class Projeto {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private Long usuarioId;

    @NotBlank
    private double orcamento;

    @NotBlank
    private double consumoEnergetico;

    @NotBlank
    private double tarifaMensal;

    @NotBlank
    private int tempoRetornoInvestimentoMeses;

    @NotBlank
    private double economia10Anos;

    @NotBlank
    private String impactoAmbiental;

    @OneToOne
    private Cliente cliente;
}