package org.revo.charity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CharityApplication {
    public static void main(String[] args) {
        SpringApplication.run(CharityApplication.class, args);
    }
}
