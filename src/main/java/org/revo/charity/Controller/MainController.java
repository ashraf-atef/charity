package org.revo.charity.Controller;

import org.revo.charity.Domain.User;
import org.revo.charity.Service.Impl.ReactiveUserDetailsServiceImpl;
import org.revo.charity.Service.JwtSigner;
import org.revo.charity.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MainController {
    private final JwtSigner jwtSigner;
    private final ReactiveUserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public MainController(JwtSigner jwtSigner, ReactiveUserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder, UserService userService) {
        this.jwtSigner = jwtSigner;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("login")
    public Mono<ResponseEntity<String>> login(@RequestBody Mono<User> user) {
        return user.flatMap(it -> userDetailsService.findByUsername(it.getEmail())
                .filter(itx -> passwordEncoder.matches(it.getPassword(), itx.getPassword())))
                .map(jwtSigner::createJwt)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(401).build());
    }

    @PostMapping("register")
    public Mono<User> register(@RequestBody Mono<User> user) {
        return user
                .map(it -> {
                    it.setPassword(passwordEncoder.encode(it.getPassword()));
                    return it;
                })
                .map(userService::save);
    }

    @GetMapping("me")
    public User getMyAccount(@AuthenticationPrincipal User user) {
        return user;
    }
}
