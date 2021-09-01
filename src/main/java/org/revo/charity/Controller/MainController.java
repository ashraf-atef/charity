package org.revo.charity.Controller;

import org.revo.charity.Domain.LoginResponse;
import org.revo.charity.Domain.User;
import org.revo.charity.Domain.UserLocation;
import org.revo.charity.Service.Impl.ReactiveUserDetailsServiceImpl;
import org.revo.charity.Service.JwtSigner;
import org.revo.charity.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
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
        List<User> optionalUser = userService.findListByEmail(user.getEmail());
        if (!optionalUser.isEmpty() &&
                // not a beneficiary as beneficiary email is empty
                optionalUser.get(0).getEmail() != null && !optionalUser.get(0).getEmail().isEmpty())
            return ResponseEntity.status(400).build();
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            UserLocation userLocation = user.getLocation();

            if (userLocation == null)
                return ResponseEntity.ok( userService.save(user));
            else {
                user.setLocation(null);
                User dbUser = userService.save(user);

                dbUser.setLocation(userLocation);
                return ResponseEntity.ok(userService.save(dbUser));
            }
        }
    }

    @PostMapping("huaweiSSO")
    public  Mono<ResponseEntity<LoginResponse>> huaweiSSO(@RequestBody User user) {
       Optional<User> optionalEmailUser = userService.findByEmail(user.getEmail());
       User dbUser;
       if (user.getEmail() == null || user.getEmail().isEmpty() || !optionalEmailUser.isPresent()) {
           Optional<User> optionalPhoneUser = userService.findByPhone(user.getPhone());
           if(!optionalPhoneUser.isPresent()) {
               user.setPassword(passwordEncoder.encode(user.getPassword()));

               UserLocation userLocation = user.getLocation();

               if (userLocation == null)
                   dbUser = userService.save(user);
               else {
                   user.setLocation(null);
                   dbUser = userService.save(user);

                   dbUser.setLocation(userLocation);

                   dbUser = userService.save(dbUser);
               }
           } else {
               dbUser = optionalPhoneUser.get();
           }
        } else {
           dbUser = optionalEmailUser.get();
       }

       return Mono.just(dbUser)
               .cast(UserDetails.class)
               .map(userDetails -> jwtSigner.createJwt(userDetails))
                .map(accessToken -> new LoginResponse(accessToken))
                .map(body -> ResponseEntity.ok(body));
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
