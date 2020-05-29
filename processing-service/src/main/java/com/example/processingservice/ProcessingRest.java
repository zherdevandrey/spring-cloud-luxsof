package com.example.processingservice;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
public class ProcessingRest {
    private ProcessingRepository repo;
    private AccountServiceClient accountServiceClient;
    private CardServiceClient cardServiceClient;
    private Environment environment;

    @RequestMapping("/issue/{accountId}")
    public String issueNewCard(@PathVariable Integer accountId) {
        log.info("create card request");
        final String card = cardServiceClient.createCard();
        log.info("created card number");
        ProcessingEntity pe = new ProcessingEntity();
        pe.setCard(card);
        pe.setAccountId(accountId);
        repo.save(pe);
        log.info("processingEntity save into db");
        return card;
    }

    @RequestMapping("/checkout/{card}")
    public boolean checkout(@PathVariable String card, @RequestParam BigDecimal sum) {
        log.info("checkout request");
        ProcessingEntity pe = repo.findByCard(card);
        if (pe == null) {
            log.info("processingEntity was not found");
            return false;
        }
        return accountServiceClient.checkout(pe.getAccountId(), sum);
    }

    @RequestMapping("/get")
    public Map<Integer, String> getByAccount(@RequestParam("account_id") List<Integer> accountIdList) {
        log.info("get account request");
        List<ProcessingEntity> list = repo.findByAccountIdIn(accountIdList);
        log.info("processing entities list size");
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (ProcessingEntity pe : list) {
            map.put(pe.getAccountId(), pe.getCard());
        }
        return map;
    }

    @HystrixCommand(fallbackMethod = "testFallBACK")
    @RequestMapping("/test/{fail}")
    public String test(@PathVariable boolean fail) {
        if (fail == true) {
            throw new RuntimeException("test exception");
        } else {
            return "ok";
        }
    }

    @GetMapping("/test/cloud/conf")
    public String testSpringCloudConfig(){
        return environment.getProperty("test.cloud.conf");
    }

}
