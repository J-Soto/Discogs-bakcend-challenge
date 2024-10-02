package com.discogs.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for RestTemplate.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Provides a RestTemplate bean.
     *
     * @return the RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}