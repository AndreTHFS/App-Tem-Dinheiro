package br.com.novo.tdinheiro.repository;

import br.com.novo.tdinheiro.entity.Simulador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SimuladorRepository extends JpaRepository<Simulador, UUID> {
}
