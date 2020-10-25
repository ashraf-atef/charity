package org.revo.charity.controller;

import org.revo.charity.domain.User;
import org.revo.charity.service.JwtSigner;
import org.revo.charity.service.UserService;
import org.revo.charity.service.impl.ReactiveUserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;

@RestController
@RequestMapping("api/account")
public class AccountController {
    private final JwtSigner jwtSigner;
    private final ReactiveUserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AccountController(JwtSigner jwtSigner, ReactiveUserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder, UserService userService) {
        this.jwtSigner = jwtSigner;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("login")
    public Mono<ResponseEntity<AbstractMap.SimpleEntry<String, String>>> login(@RequestBody Mono<User> user) {
        return user.flatMap(it -> userDetailsService.findByUsername(it.getEmail())
                .filter(itx -> passwordEncoder.matches(it.getPassword(), itx.getPassword())))
                .map(jwtSigner::createJwt)
                .map(it -> ResponseEntity.ok(new AbstractMap.SimpleEntry<>("token", it)))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent())
            return ResponseEntity.status(400).build();
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return ResponseEntity.ok(userService.save(user));
        }
    }

    @GetMapping("me")
    @PreAuthorize("isAuthenticated()")
    public Mono<User> getMyAccount(@AuthenticationPrincipal User user) {
        return Mono.just(user);
    }
}
