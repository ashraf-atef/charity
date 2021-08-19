package org.revo.charity.Controller;

import org.revo.charity.Domain.LoginResponse;
import org.revo.charity.Domain.User;
import org.revo.charity.Domain.UserLocation;
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

import java.util.Optional;
import java.util.logging.LogRecord;

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
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody Mono<User> user) {
        return user.flatMap(it -> userDetailsService.findByUsername(it.getEmail())
                        .filter(itx -> passwordEncoder.matches(it.getPassword(), itx.getPassword())))
                .map(userDetails -> jwtSigner.createJwt(userDetails))
                .map(accessToken -> new LoginResponse(accessToken))
                .map(body -> ResponseEntity.ok(body))
                .defaultIfEmpty(ResponseEntity.status(401).build());
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
    public User getMyAccount(@AuthenticationPrincipal User user) {
        return user;
    }

    @PostMapping("location")
    public ResponseEntity<User> saveUserLocation(@AuthenticationPrincipal User user, @RequestBody UserLocation location) {
        return userService.findOne(user.getId())
                .map(dbUser -> {
                    dbUser.setLocation(location);
                    return ResponseEntity.ok(userService.save(dbUser));
                })
                .orElse(ResponseEntity.status(400).build());
    }
}
