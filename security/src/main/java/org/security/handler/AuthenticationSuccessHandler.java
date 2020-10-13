//package org.security.handler;
//
//import org.security.jwt.JWTTokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.server.WebFilterExchange;
//import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//@ConditionalOnBean(JWTTokenService.class)
//public class AuthenticationSuccessHandler
//        implements ServerAuthenticationSuccessHandler {
//
//    @Autowired
//    private JWTTokenService jwtTokenService;
//    @Override
//    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
//        ServerWebExchange exchange = webFilterExchange.getExchange();
//        exchange.getResponse()
//                .getHeaders()
//                .add(HttpHeaders.AUTHORIZATION, this.getHttpAuthHeaderValue(authentication));
//        return webFilterExchange.getChain().filter(exchange);
//    }
//
//    private String getHttpAuthHeaderValue(Authentication authentication){
//        return String.join(" ","Bearer",this.tokenFromAuthentication(authentication));
//    }
//
//    private String tokenFromAuthentication(Authentication authentication){
//        return jwtTokenService.generateToken(
//                authentication.getName(),
//                authentication.getCredentials(),
//                authentication.getAuthorities());
//    }
//}