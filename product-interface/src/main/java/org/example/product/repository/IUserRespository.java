package org.example.product.repository;

import org.example.product.modal.User;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IUserRespository {

    public Mono<User> getUser(String userName);

    public List<User> getUserList();

    public User deleteUser(int id) throws Exception;
}
