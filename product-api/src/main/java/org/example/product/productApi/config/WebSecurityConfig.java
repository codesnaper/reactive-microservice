package org.example.product.productApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

import java.util.Collections;


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(
            ServerHttpSecurity http) {

        return http.authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/**").hasAuthority("ROLE_USER").anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    public ReactiveAuthenticationManager  productAuthenticationManager() {
        return authentication -> product(authentication)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(authentication
                        .getPrincipal()
                        .toString())))
                .map(b -> new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                                authentication.getCredentials(),
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                        )
                );
    }

    public Mono<String> product(Authentication authentication) {
        return Mono.justOrEmpty(authentication
                .getPrincipal()
                .toString());
    }


    public AuthenticationWebFilter authenticationWebFilter() {
        return new AuthenticationWebFilter(productAuthenticationManager());
    }

}
