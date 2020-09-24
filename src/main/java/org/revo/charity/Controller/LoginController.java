package org.revo.charity.Controller;

import org.revo.charity.Domain.User;
import org.revo.charity.Service.JwtSigner;
import org.revo.charity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
public class LoginController {
    @Autowired
    private JwtSigner jwtSigner;
    @Autowired
    private ReactiveUserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("loginx")
    public Mono<String> login(@RequestBody Mono<User> user) {
        return user.flatMap(it -> userDetailsService.findByUsername(it.getEmail())
                .filter(itx -> passwordEncoder.matches(it.getPassword(), itx.getPassword())))
                .map(it -> jwtSigner.createJwt(it));
    }

}

@Service
class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return userService.findByEmail(s).map(Mono::just).orElse(Mono.empty()).cast(UserDetails.class);
    }
}
