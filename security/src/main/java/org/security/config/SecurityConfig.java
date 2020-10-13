package org.security.config;

import org.security.modal.AuthenticationProps;
import org.security.modal.JWTProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "authentication", name = "jwt", havingValue = "true")
public class SecurityConfig {

    @Autowired
    private JWTProps jwtProps;

    @Autowired
    private AuthenticationProps authenticationProps;

    @Autowired
    @Qualifier("JWTAuthenticationWebFilter")
    private AuthenticationWebFilter jwtAuthenticationWebFilter;

    @Autowired
    @Qualifier("BearerAuthenticationFilter")
    private AuthenticationWebFilter bearerAuthenticationFilter;
    @Bean
    @ConditionalOnProperty(prefix = "authentication", name = "jwtauto", havingValue = "true")
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        String[] pathPattern = authenticationProps.getPathPattern().stream().toArray(String[]::new);
        String[] allowPath = authenticationProps.getAllowPathPattern().stream().toArray(String[]::new);
        http
                .authorizeExchange()
                .pathMatchers(authenticationProps.getTokenPath())
                .authenticated()
                .and()
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange()
                .pathMatchers(pathPattern)
                .authenticated()
                .and()
                .addFilterAt(bearerAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange()
                .pathMatchers(allowPath)
                .permitAll();

        return http.build();
    }
}
