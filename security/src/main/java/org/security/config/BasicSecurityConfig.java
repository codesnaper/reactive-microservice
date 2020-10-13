package org.security.config;

import org.security.modal.AuthenticationProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(prefix = "authentication", name = "basicauto", havingValue = "true")
@Component
public class BasicSecurityConfig {

    @Autowired
    @Qualifier("BasicAuthenticationWebFilter")
    private AuthenticationWebFilter basicAuthenticationWebFilter;

    @Autowired
    private AuthenticationProps authenticationProps;

    @Bean
    @ConditionalOnProperty(prefix = "authentication", name = "basicauto", havingValue = "true")
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        String[] allowPath = authenticationProps.getAllowPathPattern().stream().toArray(String[]::new);
        String[] authorizedPath = authenticationProps.getPathPattern().stream().toArray(String[]::new);
        http
                .authorizeExchange()
                .pathMatchers(authorizedPath)
                .authenticated()
                .and()
                .addFilterAt(basicAuthenticationWebFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange()
                .pathMatchers(allowPath)
                .permitAll();

        return http.build();
    }

}
