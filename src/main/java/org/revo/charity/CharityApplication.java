package org.revo.charity;

import org.revo.charity.Domain.User;
import org.revo.charity.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableWebFluxSecurity
public class CharityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CharityApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {
            User user = new User();
            user.setEmail("revo");
            user.setPassword(passwordEncoder.encode("revo"));
//            userService.save(user);
        };
    }
}
