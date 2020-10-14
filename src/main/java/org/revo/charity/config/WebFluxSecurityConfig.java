package org.revo.charity.config;

import org.revo.charity.domain.User;
import org.revo.charity.service.JwtSigner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebFluxSecurityConfig {
    private final JwtSigner jwtSigner;

    public WebFluxSecurityConfig(JwtSigner jwtSigner) {
        this.jwtSigner = jwtSigner;
    }

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(new jwtAuthenticationManager());
        authenticationWebFilter.setServerAuthenticationConverter(new JwtServerAuthenticationConverter());
        return http.exceptionHandling()
                .accessDeniedHandler((serverWebExchange, e) -> {
                    serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return serverWebExchange.getResponse().setComplete();
                })
                .authenticationEntryPoint((serverWebExchange, e) -> {
                    serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return serverWebExchange.getResponse().setComplete();
                })
                .and()
                .formLogin().disable()
                .authorizeExchange()
//                .pathMatchers("/swagger-ui.html").permitAll()
//                .pathMatchers("/v3/api-docs").permitAll()
//                .pathMatchers("/v3/api-docs/swagger-config").permitAll()
//                .pathMatchers("/webjars/**").permitAll()
//                .pathMatchers("/actuator","/actuator/*/**").permitAll()
                .pathMatchers("/api/account/*/**").permitAll()
                .pathMatchers("/api/*/**").authenticated()
                .anyExchange().permitAll()
                .and()
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private static class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {
        @Override
        public Mono<Authentication> convert(ServerWebExchange exchange) {
            List<String> authorization = exchange.getRequest().getHeaders().get("authorization");
            if (authorization != null && authorization.size() > 0) {
                String jwt = new String(authorization.get(0).getBytes()).substring(7);
                return Mono.just(new UsernamePasswordAuthenticationToken(jwt, jwt));
            } else return Mono.empty();
        }
    }

    private class jwtAuthenticationManager implements ReactiveAuthenticationManager {
        @Override
        public Mono<Authentication> authenticate(Authentication authentication) {
            String jwt = authentication.getCredentials().toString();
            User user = jwtSigner.validateJwt(jwt);
            return Mono.just(new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities()));
        }
    }
}
