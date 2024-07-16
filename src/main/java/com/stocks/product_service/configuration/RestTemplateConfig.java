package com.stocks.product_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 
 * Defines a bean for RestTemplate.
 */
@Configuration
public class RestTemplateConfig{

    /**
     * Creates a RestTemplate bean.
     * 
     * @return A new RestTemplate instance.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}