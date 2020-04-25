package com.example.processingservice;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "AccountService", fallback = AccountServiceFaLLBack.class)
public interface AccountServiceClient {
    @RequestMapping("/checkout/{id}")
    boolean checkout(@PathVariable("id") Integer accountId, @RequestParam("sum") BigDecimal sum);
}

@Component
class AccountServiceFaLLBack implements AccountServiceClient{
    @Override
    public boolean checkout(Integer accountId, BigDecimal sum) {
        return false;
    }
}