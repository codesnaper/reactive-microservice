package org.security.modal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
        "classpath:jwt.properties"
})
@ConditionalOnProperty(prefix = "authentication", name = "jwt", havingValue = "true")
public class JWTProps {

    @Value("${jwt.expiration.second:100}")
    private long expirationSecond;

    @Value("${jwt.issuer:security}")
    private String issuer;

    @Value("${jwt.roleName:roles}")
    private String roleName;

    @Value("${jwt.algorithm:HS256}")
    private String algorithm;

    @Value("${jwt.secretValue:secret}")
    private String secretValue;

    @Value("${jwt.apiPath:/**}")
    private String bearerApiPattern;

    @Value("${jwt.jwtApiPattern:/token}")
    private String jwtApiPattern;

    public String getJwtApiPattern() {
        return jwtApiPattern;
    }

    public void setJwtApiPattern(String jwtApiPattern) {
        this.jwtApiPattern = jwtApiPattern;
    }

    public String getBearerApiPattern() {
        return bearerApiPattern;
    }

    public void setBearerApiPattern(String bearerApiPattern) {
        this.bearerApiPattern = bearerApiPattern;
    }

    public String getSecretValue() {
        return secretValue;
    }

    public void setSecretValue(String secretValue) {
        this.secretValue = secretValue;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public long getExpirationSecond() {
        return expirationSecond;
    }

    public void setExpirationSecond(long expirationSecond) {
        this.expirationSecond = expirationSecond;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "JWTProps{" +
                "expirationSecond=" + expirationSecond +
                ", issuer='" + issuer + '\'' +
                ", roleName='" + roleName + '\'' +
                ", algorithm='" + algorithm + '\'' +
                ", secretValue='" + secretValue + '\'' +
                ", bearerApiPattern='" + bearerApiPattern + '\'' +
                ", jwtApiPattern='" + jwtApiPattern + '\'' +
                '}';
    }
}
