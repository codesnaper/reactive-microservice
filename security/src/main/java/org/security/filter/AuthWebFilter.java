package org.security.filter;

import org.security.bearer.BearerRokenAuthenticationManager;
import org.security.bearer.ServerHttpBearerAuthenticationConverter;
import org.security.handler.AuthenticationSuccessHandler;
import org.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Configuration
@ConditionalOnProperty(prefix = "authentication", name = "jwt", havingValue = "true")
public class AuthWebFilter {

    @Autowired
    ServerAuthenticationSuccessHandler serverAuthenticationSuccessHandler;

    @Autowired
    private ServerHttpBearerAuthenticationConverter serverHttpBearerAuthenticationConverter;

    @Autowired
    private BearerRokenAuthenticationManager reactiveAuthenticationManager;

    @Autowired
    private ReactiveUserDetailsService reactiveUserDetailsService;

    @Bean(name = "JWTAuthenticationWebFilter")
    @ConditionalOnProperty(prefix = "authentication", name = "jwt", havingValue = "true")
    @ConditionalOnBean({AuthenticationSuccessHandler.class})
    public AuthenticationWebFilter getAuthenticationWebFilter() {
        UserDetailsRepositoryReactiveAuthenticationManager authManager = new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
        AuthenticationWebFilter basicAuthenticationFilter = new AuthenticationWebFilter(authManager);
        basicAuthenticationFilter.setAuthenticationSuccessHandler(serverAuthenticationSuccessHandler);
        return basicAuthenticationFilter;
    }



    @Bean(name = "BearerAuthenticationFilter")
    @ConditionalOnBean({BearerRokenAuthenticationManager.class, ServerHttpBearerAuthenticationConverter.class})
    @ConditionalOnProperty(prefix = "authentication", name = "jwt", havingValue = "true")
    public AuthenticationWebFilter bearerAuthenticationFilter() {
        AuthenticationWebFilter bearerAuthenticationFilter;
        bearerAuthenticationFilter = new AuthenticationWebFilter(reactiveAuthenticationManager);
        bearerAuthenticationFilter.setAuthenticationConverter(serverHttpBearerAuthenticationConverter);
        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/product-api/**"));
        return bearerAuthenticationFilter;
    }

}
