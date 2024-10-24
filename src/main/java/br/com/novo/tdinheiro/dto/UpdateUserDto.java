package br.com.novo.tdinheiro.dto;

import br.com.novo.tdinheiro.entity.Role;

public record UpdateUserDto(String username, Role role) {
}
