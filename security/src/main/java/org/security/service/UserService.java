package org.security.service;

import org.security.Repository.IUserRespository;
import org.example.product.modal.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@ConditionalOnBean({LocalContainerEntityManagerFactoryBean.class})
@Service
@Profile("!AWS")
public class UserService implements IUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    IUserRespository userRespository;

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return userRespository.getUser(s)
                .switchIfEmpty(Mono.defer(() -> {
                    logger.error("User Not found");
                    return Mono.error(new UsernameNotFoundException("User Not found"));
                }))
                .map((user) -> {
                    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                    user.getUserRoleList().forEach(userRole -> {
                        grantedAuthorities.add(userRole.getRole());
                    });
                    return User.builder()
                            .disabled(!user.getEnabled())
                            .credentialsExpired(!user.getCredentialsNonExpired())
                            .username(user.getUserName())
                            .password(user.getPassword())
                            .authorities(grantedAuthorities)
                            .accountLocked(!user.getAccountNonLocked())
                            .accountExpired(!user.getAccountNonExpired())
                            .build();
                });
    }
}
