package com.example.upgradeservice.config;

import com.example.upgradeservice.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
    final ClientRepo clientRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(ClientRepo clientRepo, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepo = clientRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/admin/register").permitAll()
                .requestMatchers("/customer/register").permitAll()
                .requestMatchers("/expert/register").permitAll().anyRequest().authenticated()
                .and().httpBasic();

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService((username) -> clientRepo
                        .findClientByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("This %s notFound!", username))))
                .passwordEncoder(passwordEncoder);
    }

}