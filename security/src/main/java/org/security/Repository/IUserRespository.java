package org.security.Repository;

import org.example.product.modal.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IUserRespository {

    @Transactional
    public Mono<User> getUser(String userName);

    @Transactional
    public List<User> getUserList();

    @Transactional
    public User deleteUser(int id) throws Exception;

    @Transactional
    public void addDefaultUser();
}
