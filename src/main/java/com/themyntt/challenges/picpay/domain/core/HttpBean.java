package com.themyntt.challenges.picpay.domain.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpBean {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
