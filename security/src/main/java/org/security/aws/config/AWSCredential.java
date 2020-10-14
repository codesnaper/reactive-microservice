package org.security.aws.config;

import com.amazonaws.auth.AWSCredentials;
import org.security.aws.modal.AWSProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("AWS")
@Configuration
@ConditionalOnProperty({"aws.accesskey","aws.secretkey"})
public class AWSCredential implements AWSCredentials {

    private String accessKeyid;
    private String secretKeyId;

    @Autowired
    public AWSCredential(AWSProps awsProps){
        this.accessKeyid = awsProps.getAccessKeyId();
        this.secretKeyId = awsProps.getSecretKeyId();
    }

    public AWSCredential(String accessKeyid, String secretKeyId) {
        this.accessKeyid = accessKeyid;
        this.secretKeyId = secretKeyId;
    }

    @Override
    public String getAWSAccessKeyId() {
        return this.accessKeyid;
    }

    @Override
    public String getAWSSecretKey() {
        return this.secretKeyId;
    }


}
