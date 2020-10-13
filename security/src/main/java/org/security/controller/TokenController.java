package org.security.controller;

import org.security.jwt.JWTTokenService;
import org.security.modal.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/")
@ConditionalOnProperty(prefix = "authentication", name = "jwt", havingValue = "true")
public class TokenController {

    @Autowired
    JWTTokenService tokenService;

    @GetMapping("token")
    public Mono<String> getTokens(Principal principal, Authentication authentication){
        String token = "Bearer ".concat(tokenService.generateToken(authentication.getName(),authentication.getCredentials(),authentication.getAuthorities()));
//        response.setHeader(HttpHeaders.AUTHORIZATION,token);
//        response.setHeader(HttpHeaders.EXPIRES,new Long(tokenService.getExpiration()).toString());
        return Mono.justOrEmpty(String.format("Welcome %s, Token generated successfully. Your token is >> %s",principal.getName(),token));
    }

}
