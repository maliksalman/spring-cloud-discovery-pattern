package com.example.svc;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class LoadBalancedClientConfig {

    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedClientBuilder() {
        return RestClient.builder();
    }

}
