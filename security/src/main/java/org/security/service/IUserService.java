package org.security.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface IUserService extends ReactiveUserDetailsService {

}
