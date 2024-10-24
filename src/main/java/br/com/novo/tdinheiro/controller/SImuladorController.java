package br.com.novo.tdinheiro.controller;

import br.com.novo.tdinheiro.dto.RespostaSimulacaoDto;
import br.com.novo.tdinheiro.dto.SimulacaoDto;
import br.com.novo.tdinheiro.dto.UpdateSimuDto;
import br.com.novo.tdinheiro.entity.Simulador;
import br.com.novo.tdinheiro.service.SimuladorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class SImuladorController {
    private final SimuladorService simuladorService;

    public SImuladorController(SimuladorService simuladorService) {
        this.simuladorService = simuladorService;
    }

    @PostMapping("/simulacao")
    public ResponseEntity<RespostaSimulacaoDto> simulacao(@RequestBody @Valid SimulacaoDto simulacaoDto){
        return ResponseEntity.ok(simuladorService.gerarSimulacao(simulacaoDto));
    }

    @GetMapping("/simulador")
    public ResponseEntity<List<Simulador>> findAll(){
        return  ResponseEntity.ok(simuladorService.findAll());
    }

    @GetMapping("/simulador/{simuladorId}")
    public ResponseEntity<UUID> findByI(@PathVariable(value = "simuladorId") UUID id){
        var simulador= simuladorService.findById(id);
        return ResponseEntity.ok(simulador.getSimuladorId());
    }
    @PutMapping("/simulador/{simuladorId}")
    public ResponseEntity<Void> atualizarSimulacao(@PathVariable(value = "simuladorId") UUID id,
                                                   @RequestBody UpdateSimuDto simuDto){
        simuladorService.atualidar(id,simuDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/simulador/{simuladorId}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "simuladorId") UUID id){
        simuladorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handlerIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handlerEntityNotFound(EntityNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }

}
