package org.security.bearer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.security.modal.JWTProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.function.Predicate;

@Component
@ConditionalOnBean(JWTProps.class)
/**
 * Class used to verify the JWT and it is created in Bean when JWTProp class is there.
 */
public class JWTCustomVerifier {

    private JWSVerifier jwsVerifier;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     *
     * @param jwtProps jwt configuration class
     */
    @Autowired
    public JWTCustomVerifier(JWTProps jwtProps) {
        this.jwsVerifier = this.buildJWSVerifier(jwtProps);
    }

    /**
     * Check whether token is valid or not
     * @param token
     * @return Return SignedJWT if it is valid token
     */
//    TODO: Need to handle error , if error is there then say 401
    public Mono<SignedJWT> check(String token) {
        logger.debug("Checking token for verification");
        return Mono.justOrEmpty(createJWS(token))
                .filter(isNotExpired)
                .filter(validSignature);
    }

    /**
     * predicate function to check if token is not expired
     */
    private Predicate<SignedJWT> isNotExpired = token ->
            getExpirationDate(token).after(Date.from(Instant.now()));

    /**
     * predicate function to see if signature of token is valid or not
     */
    private Predicate<SignedJWT> validSignature = token -> {
        try {
            return token.verify(this.jwsVerifier);
        } catch (JOSEException e) {
            e.printStackTrace();
            return false;
        }
    };

    /**
     * Used to build JWS verify from secret value from which token was genrated.
     * @param jwtProps JWTProp configuration class
     * @return MACVerifier
     */
    private MACVerifier buildJWSVerifier(JWTProps jwtProps) {
        logger.info("Building JWS Verify from secret key");
        try {
            return new MACVerifier(jwtProps.getSecretValue());
        } catch (JOSEException e) {
            logger.error("Failed in building JWS Verify",e);
            return null;
        }
    }

    /**
     * Function used to extract the information from JWT Token
     * @param token
     * @return SignedJWT
     */
    private SignedJWT createJWS(String token) {
        try {
            return SignedJWT.parse(token);
        } catch (ParseException e) {
            logger.error("Error while creating SignedJWT from token",e);
            return null;
        }
    }

    /**
     * Function used to get the expiry date from signed token from claimset
     * @param token
     * @return Date
     */
    private Date getExpirationDate(SignedJWT token) {
        try {
            return token.getJWTClaimsSet()
                    .getExpirationTime();
        } catch (ParseException e) {
            logger.error("Failed to get Date from token",e);
            return null;
        }
    }
}
