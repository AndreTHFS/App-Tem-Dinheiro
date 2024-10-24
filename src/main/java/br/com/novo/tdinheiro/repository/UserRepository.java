package br.com.novo.tdinheiro.repository;

import br.com.novo.tdinheiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
   Optional <User> findByUsername(String username);
   public boolean existsByUsername(String username);
}
