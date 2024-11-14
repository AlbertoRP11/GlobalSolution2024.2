package com.fiap.sunwise.service;

import com.fiap.sunwise.model.Projeto;
import com.fiap.sunwise.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public Projeto saveProjeto(Projeto projeto) {
        calcularCamposProjeto(projeto);
        return projetoRepository.save(projeto);
    }


    public Projeto getProjetoById(Long id) {
        Optional<Projeto> projeto = projetoRepository.findById(id);
        return projeto.orElse(null);
    }

    public List<Projeto> getAllProjetos() {
        return projetoRepository.findAll();
    }

    public Projeto updateProjeto(Long id, Projeto projeto) {
        if (projetoRepository.existsById(id)) {
            projeto.setId(id);
            return projetoRepository.save(projeto);
        }
        return null;
    }

    public boolean deleteProjeto(Long id) {
        if (projetoRepository.existsById(id)) {
            projetoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void calcularCamposProjeto(Projeto projeto) {
        double economiaMensal = projeto.getConsumoEnergetico() * projeto.getTarifaMensal();
        projeto.setEconomiaMensal(economiaMensal);

        int tempoRetornoMeses = (int) Math.ceil(projeto.getOrcamento() / economiaMensal);
        projeto.setTempoRetornoInvestimentoMeses(tempoRetornoMeses);

        int anos = tempoRetornoMeses / 12;
        int meses = tempoRetornoMeses % 12;
        String retornoEmAnos = String.format("%d anos e %d meses", anos, meses);
        projeto.setRetornoEmAnos(retornoEmAnos);

        double economia10Anos = economiaMensal * 12 * 10;
        projeto.setEconomia10Anos(economia10Anos);

        double co2Evitado10Anos = projeto.getConsumoEnergetico() * 0.4 * 12 * 10;
        projeto.setCo2Evitado10Anos(co2Evitado10Anos);

        projeto.setImpactoAmbiental(
            String.format("Em 10 anos, o cliente evitará a emissão de aproximadamente %.2f toneladas de CO₂.",
                    co2Evitado10Anos / 1000)
        );

    }

}