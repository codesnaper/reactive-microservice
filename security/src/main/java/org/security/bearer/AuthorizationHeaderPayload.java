package org.security.bearer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Used for extracting the the required header from http request
 */
public class AuthorizationHeaderPayload {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationHeaderPayload.class.getClass().getName());

    /**
     * it extract the authorization header from http request
     * @param serverWebExchange it provide the httprequest
     * @return Authorization code
     */
    public static Mono<String> extract(ServerWebExchange serverWebExchange) {
        logger.info("Extracting %s from requests",HttpHeaders.AUTHORIZATION);
        return Mono.justOrEmpty(serverWebExchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION));
    }
}
