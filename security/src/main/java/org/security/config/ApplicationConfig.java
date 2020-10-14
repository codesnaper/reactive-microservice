package org.security.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.security.aws.modal.AWSProps;
import org.security.modal.AuthenticationProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration

public class ApplicationConfig {

    @Autowired
    private AuthenticationProps authenticationProps;

    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired(required = false)
    private AWSProps awsProps;

    @Bean
    @ConditionalOnMissingBean({LocalContainerEntityManagerFactoryBean.class})
    public ReactiveUserDetailsService reactiveUserDetailsService(){
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(authenticationProps.getDefaultUser())
                .password(authenticationProps.getDefaultPassword())
                .roles(authenticationProps.getDefaultRole())
                .build();
        return new MapReactiveUserDetailsService(user);
    }

}
