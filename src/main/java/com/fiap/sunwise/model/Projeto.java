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
    @Column(name = "NOME_PROJETO", nullable = false)
    private String nomeProjeto;

    @NotNull
    @Column(name = "ORCAMENTO", nullable = false)
    private double orcamento;

    @NotNull
    @Column(name = "TARIFA_MENSAL", nullable = false)
    private double tarifaMensal;

    @Column(name = "TEMPO_RETORNO_INVESTIMENTO_MESES")
    private int tempoRetornoInvestimentoMeses;

    @Column(name = "ECONOMIA_MENSAL")
    private double economiaMensal;

    @Column(name = "RETORNO_EM_ANOS")
    private String retornoEmAnos;

    @Column(name = "ECONOMIA_10_ANOS")
    private double economia10Anos;

    @Column(name = "IMPACTO_AMBIENTAL")
    private String impactoAmbiental;

    @Column(name = "CO2_EVITADO_10_ANOS")
    private double co2Evitado10Anos;

    @NotNull
    private Long userId;

    @NotNull
    @ManyToOne
    private Cliente cliente;
    }
