package org.security.aws.config;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("AWS")
public class ApplicationConfig {

    @Autowired(required = false)
    private AWSCredentialProvider awsCredentialProvider;

    @ConditionalOnBean(AWSCredentialProvider.class)
    @Bean
    public AWSCognitoIdentityProvider awsCognitoIdentityProvider(){
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(awsCredentialProvider).build();
    }
}
