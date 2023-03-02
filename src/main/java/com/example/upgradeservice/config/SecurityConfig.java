package com.example.upgradeservice.config;

import com.example.upgradeservice.repository.ClientRepo;
import com.example.upgradeservice.repository.ManagerRepo;
import com.example.upgradeservice.repository.TechnicianRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig{

    private final ClientRepo clientRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TechnicianRepo technicianRepo;
    private final ManagerRepo managerRepo;

    public SecurityConfig(ClientRepo clientRepo, BCryptPasswordEncoder passwordEncoder, TechnicianRepo technicianRepo, ManagerRepo managerRepo) {
        this.clientRepo = clientRepo;
        this.passwordEncoder = passwordEncoder;
        this.technicianRepo = technicianRepo;
        this.managerRepo = managerRepo;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/Client/register").permitAll()
                .requestMatchers("/Technician/register").permitAll()
                .requestMatchers("/Manager/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic();

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService((username) -> clientRepo
                        .findClientByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("This %s notFound!", username))))
                .passwordEncoder(passwordEncoder).and()
                .userDetailsService((username) -> technicianRepo
                        .findClientByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("This %s notFound!", username))))
                .passwordEncoder(passwordEncoder).and()
                .userDetailsService((username) -> managerRepo
                        .findManagerByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("This %s notFound!", username))))
                .passwordEncoder(passwordEncoder);
    }

}
