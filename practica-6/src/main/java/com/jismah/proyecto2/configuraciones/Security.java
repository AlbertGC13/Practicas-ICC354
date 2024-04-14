package com.jismah.proyecto2.configuraciones;


import com.jismah.proyecto2.servicios.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Security {

    private DataSource dataSource;
    @Value("${query.user-jdbc}")
    private String queryUsuario;
    @Value("${query.rol-jdbc}")
    private String queryRol;
    //Opción JPA
    private SecurityService securityService;
    private PasswordEncoder passwordEncoder;
    private JWTAuth jwtAuth;

    public Security(DataSource dataSource, SecurityService securityService, PasswordEncoder passwordEncoder, JWTAuth jwtAuth) {
        this.dataSource = dataSource;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuth = jwtAuth;
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(securityService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        //Servicio.
        System.out.println("Autentificación en JPA");
        auth.userDetailsService(securityService)
                .passwordEncoder(passwordEncoder);


        return auth.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterApi(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/mock/jwt/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authorization -> {
                    try {
                        authorization
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/mock/jwt/**")).authenticated()
                                .and().sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .authenticationProvider(authenticationProvider())
                                .addFilterBefore(jwtAuth, UsernamePasswordAuthenticationFilter.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.authorizeHttpRequests(authorization ->
                        authorization
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/")).hasAnyRole("ADMIN", "USER") //permitiendo llamadas a esas urls.
                                .requestMatchers(mvc.pattern("/h2-console/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**")).hasRole("ADMIN")
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/user/**")).hasRole("USER")
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/logout")).hasAnyRole("ADMIN", "USER")
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/logout")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/mock/**")).permitAll()
                                .anyRequest().authenticated() //cualquier llamada debe ser validada
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .logoutRequestMatcher(AntPathRequestMatcher.antMatcher("/logout"))
                        .permitAll());
        return http.build();
    }
}
