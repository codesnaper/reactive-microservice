package org.security.jwt;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.security.modal.JWTProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Configuration
@ConditionalOnProperty(prefix = "authentication", name = "jwt", havingValue = "true")
@ConditionalOnBean(JWTProps.class)
public class JWTTokenService {

    @Autowired
    private JWTProps jwtProps;

    @Autowired
    private JWTCustomSigner jwtCustomSigner;

    public String generateToken(String subject, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        SignedJWT signedJWT;
        JWTClaimsSet claimsSet;

        claimsSet = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer(jwtProps.getIssuer())
                .expirationTime(new Date(getExpiration()))
                .claim(jwtProps.getRoleName(), authorities
                        .stream()
                        .map(GrantedAuthority.class::cast)
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .build();

        signedJWT = new SignedJWT(new JWSHeader(new JWSAlgorithm(jwtProps.getAlgorithm())), claimsSet);

        try {
            signedJWT.sign(jwtCustomSigner.getSigner());
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return signedJWT.serialize();
    }

    /**
     * Returns a millisecond time representation 24hrs from now
     * to be used as the time the currently token will be valid
     *
     * @return Time representation 24 from now
     */
    private long getExpiration(){
        return new Date().toInstant()
                .plusSeconds(jwtProps.getExpirationSecond())
                .toEpochMilli();
    }
}