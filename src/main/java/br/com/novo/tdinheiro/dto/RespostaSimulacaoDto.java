package br.com.novo.tdinheiro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;


public record RespostaSimulacaoDto(
        String nome,
         @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#.##")
        Double valor,
        @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "#.##")
        Double Parcela) { }
