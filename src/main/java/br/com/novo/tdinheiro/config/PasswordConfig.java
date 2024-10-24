package br.com.novo.tdinheiro.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PasswordConfig {

    @Value("${rsa.password}")
    private String rsaPassword;

    @Bean
    public String getRsaPassword() {
        //log.info("Rsa password Loader: {}" + rsaPassword);
        return rsaPassword;
    }
}
