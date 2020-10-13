package org.security.controller;

import org.security.modal.Token;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@ConditionalOnProperty(prefix = "authentication", name = "jwtauto", havingValue = "true")
public class TokenController {

    @GetMapping("token")
    public Mono<String> getTokens(){
        return Mono.justOrEmpty("success");
    }
}
