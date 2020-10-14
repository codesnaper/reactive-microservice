package org.security.aws.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.security.aws.config.AWSCredentialProvider;
import org.security.aws.modal.AWSProps;
import org.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Profile("AWS")
@Service
@ConditionalOnBean(AWSCredentialProvider.class)
public class AWSUserService implements IUserService {

    @Autowired
    private AWSCredentialProvider awsCredentialProvider;

    @Autowired
    AWSProps awsProps;

    @Autowired
    private AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        AdminGetUserRequest adminGetUserRequest = new AdminGetUserRequest()
                .withUserPoolId(awsProps.getUserPoolId())
                .withUsername(s);
        AdminGetUserResult adminGetUserResult = awsCognitoIdentityProvider.adminGetUser(adminGetUserRequest);
        User.UserBuilder userBuilder = User.builder().username(adminGetUserResult.getUsername());
        adminGetUserResult.getUserAttributes().forEach(item->{
            switch (item.getName()){
                case "password":
                    userBuilder.password(item.getValue());
                    break;

                case "accountExpired":
                    userBuilder.accountExpired(Boolean.parseBoolean(item.getValue()));
                    break;

                case "accountLocked":
                    userBuilder.accountLocked(Boolean.parseBoolean(item.getValue()));
                    break;

                case "credentialsExpired":
                    userBuilder.credentialsExpired(Boolean.parseBoolean(item.getValue()));
                    break;

                case "roles":
                    userBuilder.roles(item.getValue().split(","));
                    break;

            }
            userBuilder.disabled(!adminGetUserResult.isEnabled());
        });
        return Mono.justOrEmpty(userBuilder.build());
    }
}
