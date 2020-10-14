package org.security.aws.repository;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import org.example.product.modal.User;
import org.security.Repository.IUserRespository;
import org.security.modal.AuthenticationProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("AWS")
public class AWSUserRepository implements IUserRespository {


    @Autowired
    AuthenticationProps authenticationProps;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AWSCognitoIdentityProvider awsCognitoIdentityProvider;

    @Override
    public Mono<User> getUser(String userName) {
        return null;
    }

    @Override
    public List<User> getUserList() {
        return null;
    }

    @Override
    public User deleteUser(int id) throws Exception {
        return null;
    }

    @Override
    public void addDefaultUser() {
        SignUpRequest signUpRequest = new SignUpRequest();
        List<AttributeType> attributeTypes = new ArrayList<>();
        AttributeType attributeType = new AttributeType();
        attributeType.setName("accountExpired");
        attributeType.setValue("false");
        attributeTypes.add(attributeType);
        attributeType.setName("accountLocked");
        attributeType.setValue("false");
        attributeTypes.add(attributeType);
        attributeType.setName("credentialsExpired");
        attributeType.setValue("false");
        attributeTypes.add(attributeType);
        attributeType.setName("roles");
        attributeType.setValue(authenticationProps.getDefaultRole());
        attributeType.setName("password");
        attributeType.setValue(passwordEncoder.encode(authenticationProps.getDefaultPassword()));
        attributeTypes.add(attributeType);
        signUpRequest.setUserAttributes(attributeTypes);
        signUpRequest.setUsername(authenticationProps.getDefaultUser());
        signUpRequest.setPassword(authenticationProps.getDefaultPassword());
        try {
            awsCognitoIdentityProvider.signUp(signUpRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
