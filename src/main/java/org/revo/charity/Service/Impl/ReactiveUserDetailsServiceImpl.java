package org.revo.charity.Service.Impl;

import org.revo.charity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
    private final UserService userService;

    public ReactiveUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return userService.findByEmail(s).map(Mono::just).orElse(Mono.empty()).cast(UserDetails.class);
    }
}
