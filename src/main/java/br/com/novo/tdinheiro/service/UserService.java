package br.com.novo.tdinheiro.service;

import br.com.novo.tdinheiro.config.TokenConfig;
import br.com.novo.tdinheiro.dto.CreateUserDto;
import br.com.novo.tdinheiro.dto.LoginRequest;
import br.com.novo.tdinheiro.dto.LoginResponse;
import br.com.novo.tdinheiro.dto.UpdateUserDto;
import br.com.novo.tdinheiro.entity.Role;
import br.com.novo.tdinheiro.entity.User;
import br.com.novo.tdinheiro.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfig token;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenConfig token) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.token = token;

    }

    public LoginResponse login (LoginRequest loginRequest){
        var user = userRepository.findByUsername(loginRequest.username());
        if(user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)){
            throw new AuthenticationException("Usuário ou senha invalidos") {

            };
        }
        return token.login(loginRequest);
    }

    @Transactional
    public User createUser(CreateUserDto userDto){

        if (userRepository.existsByUsername(userDto.username())){
           throw new IllegalArgumentException("Usuário já existe");
        }
        var newUser = new User();
        newUser.setUsername(userDto.username());
        newUser.setPassword(passwordEncoder.encode(userDto.password()));
        newUser.setRole(Role.USER);
        userRepository.save(newUser);
        return newUser;
    }

    public Optional<User> findById(UUID id) {
                return Optional.of(
                        userRepository.findById(
                                UUID.fromString(String.valueOf(id))).orElseThrow(
                                        () -> new EntityNotFoundException("Usuário não existe")));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void updateUser(UUID id, UpdateUserDto userDto){
       var user=   userRepository.findById(id).orElseThrow(
                            ()-> new EntityNotFoundException("Usuário não encontrado"));
                 user.setUsername(userDto.username());
                 user.setRole(userDto.role());
                 userRepository.save(user);
    }

    public User findByUsername(String name){
        return userRepository.findByUsername(name).orElseThrow(() ->new IllegalArgumentException("Usuário não existe"));
    }

    public boolean existsByUsername(String name){
        return userRepository.existsByUsername(name);
    }

    public void deleteUserById(String id){

        if(!userRepository.existsById(UUID.fromString(id))){
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        userRepository.deleteById(UUID.fromString(id));

    }
}
