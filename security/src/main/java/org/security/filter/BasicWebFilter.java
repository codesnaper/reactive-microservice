package org.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@ConditionalOnProperty(prefix = "authentication", name = "basic", havingValue = "true")
@Configuration
public class BasicWebFilter {
    @Autowired
    private ReactiveUserDetailsService reactiveUserDetailsService;

    @Bean(name = "BasicAuthenticationWebFilter")
    @ConditionalOnProperty(prefix = "authentication", name = "basic", havingValue = "true")
    public AuthenticationWebFilter basicAuthenticationWebFilter() {
        UserDetailsRepositoryReactiveAuthenticationManager authManager = new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
        AuthenticationWebFilter basicAuthenticationFilter = new AuthenticationWebFilter(authManager);
        return basicAuthenticationFilter;
    }
}
