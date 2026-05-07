package br.com.sneacker.sneackerjava.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.GET, "/tenis/listar/{email}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/tenis/listar").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/tenis/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/tenis/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/tenis/criar").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/usuario/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/usuario/auth/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/usuario/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/usuario/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/musica/criar").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/musica/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/musica/{id}").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
