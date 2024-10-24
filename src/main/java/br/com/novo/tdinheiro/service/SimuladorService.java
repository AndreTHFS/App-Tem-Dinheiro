package br.com.novo.tdinheiro.service;

import br.com.novo.tdinheiro.dto.RespostaSimulacaoDto;
import br.com.novo.tdinheiro.dto.SimulacaoDto;
import br.com.novo.tdinheiro.dto.UpdateSimuDto;
import br.com.novo.tdinheiro.entity.Simulador;
import br.com.novo.tdinheiro.repository.SimuladorRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SimuladorService {

    private final SimuladorRepository simuladorRepository;


    public SimuladorService(SimuladorRepository simuladorRepository) {
        this.simuladorRepository = simuladorRepository;

    }

    public RespostaSimulacaoDto gerarSimulacao(SimulacaoDto dto){


        Simulador simulador = new Simulador();

        if (dto.valor()  < 0 || dto.fone().isEmpty() || dto.name().isEmpty() || dto.orgao()==null){
           throw new IllegalArgumentException("Faltou preencher algum campo");
        }else {
            simulador.setNomeLead(dto.name());
            simulador.setFoneLead(dto.fone());
            simulador.setOrgao(dto.orgao());
            simulador.setValor(dto.valor());
            simulador.setParcela(parcela(dto.valor()));
            simuladorRepository.save(simulador);
            return new RespostaSimulacaoDto(simulador.getNomeLead(),
                    simulador.getValor(), (simulador.getParcela()));
        }
    }

    public Double parcela(double valor){
        return valor* 0.02340;
    }

    public List<Simulador> findAll(){
        return simuladorRepository.findAll();
    }

    public Simulador findById(UUID id) {
        return simuladorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Simulador não encontrado"));
    }

    public void deleteById(UUID id){
        var simulador =simuladorRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Simulação não encontrada"));
        simuladorRepository.deleteById(simulador.getSimuladorId());
    }

    public void atualidar(UUID id, UpdateSimuDto simuDto){

       var simulador =  simuladorRepository.findById(id).orElseThrow(
               ()->new EntityNotFoundException("Simulação não encontrada"));

       simulador.setNomeLead(simuDto.nome());
       simulador.setFoneLead(simuDto.fone());
       simuladorRepository.save(simulador);
    }
}
