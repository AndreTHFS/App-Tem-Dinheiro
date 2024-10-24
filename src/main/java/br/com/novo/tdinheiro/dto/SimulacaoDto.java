package br.com.novo.tdinheiro.dto;

import br.com.novo.tdinheiro.entity.Orgao;


public record SimulacaoDto(String name,String fone, Orgao orgao,
                           Double valor) {
}
