package org.security.jwt;

import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import org.security.modal.JWTProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "authentication", name = "jwt", havingValue = "true")
@ConditionalOnBean(JWTProps.class)
public class JWTCustomSigner {

    private JWSSigner signer;

    @Autowired
    public JWTCustomSigner(JWTProps jwtProps) {
        try {
            this.signer = new MACSigner(jwtProps.getSecretValue());
        } catch (KeyLengthException e) {
            this.signer = null;
        }
    }

    public JWSSigner getSigner() {
        return this.signer;
    }
}
