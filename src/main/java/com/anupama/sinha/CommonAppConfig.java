package com.anupama.sinha;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonAppConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}