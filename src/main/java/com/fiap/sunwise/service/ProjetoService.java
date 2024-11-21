package com.fiap.sunwise.service;

import com.fiap.sunwise.model.Cliente;
import com.fiap.sunwise.model.Projeto;
import com.fiap.sunwise.repository.ClienteRepository;
import com.fiap.sunwise.repository.ProjetoRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void inserirProjeto(Projeto projeto) {
        calcularCamposProjeto(projeto);
        entityManager.createNativeQuery(
                "BEGIN pkg_projeto.inserir_projeto(NULL, :nomeProjeto, :orcamento, :tarifaMensal, :tempoRetorno, :clienteId, :usuarioId, :economiaMensal, :retornoEmAnos, :economia10Anos, :impactoAmbiental, :co2Evitado10Anos); END;")
                .setParameter("nomeProjeto", projeto.getNomeProjeto())
                .setParameter("orcamento", projeto.getOrcamento())
                .setParameter("tarifaMensal", projeto.getTarifaMensal())
                .setParameter("tempoRetorno", projeto.getTempoRetornoInvestimentoMeses())
                .setParameter("clienteId", projeto.getCliente().getId())
                .setParameter("usuarioId", projeto.getUsuarioId())
                .setParameter("economiaMensal", projeto.getEconomiaMensal())
                .setParameter("retornoEmAnos", projeto.getRetornoEmAnos())
                .setParameter("economia10Anos", projeto.getEconomia10Anos())
                .setParameter("impactoAmbiental", projeto.getImpactoAmbiental())
                .setParameter("co2Evitado10Anos", projeto.getCo2Evitado10Anos())
                .executeUpdate();
    }

    @Transactional
    public Projeto atualizarProjeto(Long id, Projeto projeto) {
        if (!projetoRepository.existsById(id)) {
            throw new IllegalArgumentException("Projeto com ID " + id + " não encontrado.");
        }

        Projeto projetoExistente = projetoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Erro ao buscar o projeto com ID " + id));

        // Atualiza os campos fornecidos pelo usuário
        projetoExistente.setNomeProjeto(projeto.getNomeProjeto());
        projetoExistente.setOrcamento(projeto.getOrcamento());
        projetoExistente.setTarifaMensal(projeto.getTarifaMensal());

        // Atualiza o cliente (se fornecido)
        if (projeto.getCliente() != null && projeto.getCliente().getId() != null) {
            Long clienteId = projeto.getCliente().getId();
            if (!clienteRepository.existsById(clienteId)) {
                throw new IllegalArgumentException("Cliente com ID " + clienteId + " não encontrado.");
            }
            Cliente cliente = new Cliente();
            cliente.setId(clienteId);
            projetoExistente.setCliente(cliente);
        }

        calcularCamposProjeto(projetoExistente);

        entityManager.createNativeQuery(
                "BEGIN pkg_projeto.atualizar_projeto(:id,:nomeProjeto, :orcamento, :tarifaMensal, :tempoRetorno, :clienteId, :usuarioId, :economiaMensal, :retornoEmAnos, :economia10Anos, :impactoAmbiental, :co2Evitado10Anos); END;")
                .setParameter("nomeProjeto", projetoExistente.getNomeProjeto())
                .setParameter("orcamento", projetoExistente.getOrcamento())
                .setParameter("tarifaMensal", projetoExistente.getTarifaMensal())
                .setParameter("tempoRetorno", projetoExistente.getTempoRetornoInvestimentoMeses())
                .setParameter("clienteId", projetoExistente.getCliente().getId())
                .setParameter("usuarioId", projetoExistente.getUsuarioId())
                .setParameter("economiaMensal", projetoExistente.getEconomiaMensal())
                .setParameter("retornoEmAnos", projetoExistente.getRetornoEmAnos())
                .setParameter("economia10Anos", projetoExistente.getEconomia10Anos())
                .setParameter("impactoAmbiental", projetoExistente.getImpactoAmbiental())
                .setParameter("co2Evitado10Anos", projetoExistente.getCo2Evitado10Anos())
                .executeUpdate();

        return projetoExistente;

    }

    @Transactional
    public boolean deletarProjeto(Long id) {
        if (projetoRepository.existsById(id)) { // Verifica se o projeto existe
            entityManager.createNativeQuery("BEGIN pkg_projeto.deletar_projeto(:id); END;")
                    .setParameter("id", id)
                    .executeUpdate();
            return true; 
        }
        return false;
    }

    public Projeto getProjetoById(Long id) {
        Optional<Projeto> projeto = projetoRepository.findById(id);
        return projeto.orElse(null);
    }

    public List<Projeto> getProjetosByUsuarioId(Long usuarioId) {
        return projetoRepository.findByUsuarioId(usuarioId);
    }

    public List<Projeto> getAllProjetos() {
        return projetoRepository.findAll();
    }

    private void calcularCamposProjeto(Projeto projeto) {
        int taxaEnel = 50;
        double economiaMensal = projeto.getTarifaMensal() - taxaEnel;
        projeto.setEconomiaMensal(economiaMensal);

        int tempoRetornoMeses = (int) Math.ceil(projeto.getOrcamento() / economiaMensal);
        projeto.setTempoRetornoInvestimentoMeses(tempoRetornoMeses);

        int anos = tempoRetornoMeses / 12;
        int meses = tempoRetornoMeses % 12;
        String retornoEmAnos = String.format("%d anos e %d meses", anos, meses);
        projeto.setRetornoEmAnos(retornoEmAnos);

        double economia10Anos = economiaMensal * 12 * 10;
        projeto.setEconomia10Anos(economia10Anos);

        double precoKwh = 0.50;
        double consumoEnergetico = projeto.getTarifaMensal() / precoKwh;
        double co2Evitado10Anos = consumoEnergetico * 0.4 * 12 * 10;
        projeto.setCo2Evitado10Anos(co2Evitado10Anos);

        projeto.setImpactoAmbiental(
                String.format("Em 10 anos, o cliente evitará a emissão de aproximadamente %.2f toneladas de CO₂.",
                        co2Evitado10Anos / 1000));

    }

}