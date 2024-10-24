package br.com.novo.tdinheiro.config;

import br.com.novo.tdinheiro.dto.LoginRequest;
import br.com.novo.tdinheiro.dto.LoginResponse;
import br.com.novo.tdinheiro.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;


@Configuration
public class TokenConfig {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;

    public TokenConfig(JwtEncoder jwtEncoder, UserRepository userRepository) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest loginRequest){
         var user = userRepository.findByUsername(
                 loginRequest.username()).orElseThrow(
                 ()-> new EntityNotFoundException("Usuário não existe"));

         var expires = 360L;
         var scopes = user.getRole().name();

         var claims = JwtClaimsSet.builder()
                 .issuer("TD-backend")
                 .subject(loginRequest.username())
                 .claim("scope", scopes)
                 .issuedAt(Instant.now())
                 .expiresAt(Instant.now().plusSeconds(expires))
                 .build();
         var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

         return new LoginResponse(jwtValue, expires);
    }
}
