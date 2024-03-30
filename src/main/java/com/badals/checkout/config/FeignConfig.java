package com.badals.checkout.config;

import com.badals.checkout.service.integration.payment.thawani.ThawaniApiClient;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

//    @Value("${thawani.api.url}")
    @Value("https://checkout.thawani.om/api/v1")
    private String thawaniApiUrl;

//    @Value("${thawani.api.key}")
//    @Value("rRQ26GcsZzoEhbrP2HZvLYDbn9C9et")
//    private String thawaniApiKey;


    @Bean
    public ThawaniApiClient thawaniApiClient() {
        return Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .requestInterceptor(requestTemplate -> {
                    requestTemplate.header("Accept", "application/json");
                    requestTemplate.header("Content-Type", "application/json");
//                    requestTemplate.header("thawani-api-key", thawaniApiKey);
                })
                .target(ThawaniApiClient.class, thawaniApiUrl);
    }
}

