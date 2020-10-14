package org.security.aws.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("AWS")
@ConditionalOnBean(AWSCredential.class)
@Configuration
public class AWSCredentialProvider implements AWSCredentialsProvider {

    @Autowired
    private AWSCredential awsCredential;

    @Override
    public AWSCredentials getCredentials() {
        return awsCredential;
    }

//    TODO: Refresh Implementation
    @Override
    public void refresh() {

    }
}
