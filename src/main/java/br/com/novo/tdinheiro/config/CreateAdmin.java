package br.com.novo.tdinheiro.config;

import br.com.novo.tdinheiro.entity.Role;
import br.com.novo.tdinheiro.entity.User;
import br.com.novo.tdinheiro.repository.UserRepository;

import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class CreateAdmin implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordConfig password;

    public CreateAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordConfig password) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.password = password;
    }

    @Override
    public void run(String... args) throws Exception {

        var userAdmin = userRepository.findByUsername("admin");
        userAdmin.ifPresentOrElse(
                u -> log.info("Usuário já existe")
                ,
                ()->{ var user = new User();
                        user.setUsername("admin");
                        user.setPassword(passwordEncoder.encode(password.getRsaPassword()));
                        user.setRole(Role.ADMIN);
                        userRepository.save(user);
                 }
        );
    }
}