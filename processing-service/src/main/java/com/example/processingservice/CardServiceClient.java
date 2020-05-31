package com.example.processingservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "account-service", fallback = CardServiceFallBACK.class)
public interface CardServiceClient {
    @RequestMapping("/create")
    String createCard();
}

@Component
@Slf4j
class CardServiceFallBACK implements CardServiceClient{

    @Override
    public String createCard() {
        return null;
    }
}

