package br.com.novo.tdinheiro.service;

import br.com.novo.tdinheiro.dto.CreateUserDto;
import br.com.novo.tdinheiro.dto.LoginRequest;
import br.com.novo.tdinheiro.dto.LoginResponse;
import br.com.novo.tdinheiro.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServiceInterface {

    public LoginResponse login (LoginRequest loginRequest);
    public User createUser(CreateUserDto userDto);
    public Optional<User> findById(UUID id);
    public List<User> findAll();
}
