package com.fiap.sunwise.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "T_SNW_PROJETO")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long usuarioId;
    
    @NotNull
    private String nomeProjeto;

    @NotNull
    private double orcamento;

    @NotNull
    private double consumoEnergetico;

    @NotNull
    private double tarifaMensal;

    private int tempoRetornoInvestimentoMeses;

    private double economiaMensal;

    private String retornoEmAnos;

    private double economia10Anos;

    private String impactoAmbiental;

    private double co2Evitado10Anos;

    @OneToOne
    private Cliente cliente;
}
