package org.security.Repository;

import javassist.NotFoundException;
import org.example.product.modal.Role;
import org.example.product.modal.User;
import org.example.product.modal.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Repository
@ConditionalOnBean({LocalContainerEntityManagerFactoryBean.class})
public class UserRepository implements IUserRespository{

    private Logger logger = LoggerFactory.getLogger(UserRepository.class);
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager entityManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public Mono<User> getUser(String userName) {
        logger.debug("Fetching user Data from DB");
        try {
            TypedQuery<User> userTypedQuery = entityManager.createNamedQuery("findUser", User.class);
            userTypedQuery.setParameter("userName", userName);
            return Mono.justOrEmpty(userTypedQuery.getSingleResult());
        }
        catch (PersistenceException ex){
            logger.error("Error while fetching data",ex);
            return Mono.empty();
        }

    }

    @Transactional
    public void addDefaultUser(){
        User user = new User();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setDeletedFalg(false);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode("password"));
        user.setUserCreationDate(new Date());
        user.setUserName("user");
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        user.setUserRoleList(Collections.singletonList(userRole));
        Role role = new Role();
        role.setRoleName("USER");
        userRole.setRole(role);
        role.setUserRole(userRole);
        entityManager.persist(user);
        entityManager.persist(role);
        entityManager.persist(userRole);
        entityManager.flush();

    }
    @Transactional
    public List<User> getUserList() {
        return null;
    }
    @Transactional
    public User deleteUser(int id) throws NotFoundException {
        User user = this.findById(id);
        entityManager.remove(user);
        return user;
    }
    @Transactional
    public User findById(int id) throws NotFoundException {
        User user =entityManager.find(User.class,id);
        if(user!=null){
            return user;
        }
        throw  new NotFoundException("Data not found for id "+id);
    }
}
