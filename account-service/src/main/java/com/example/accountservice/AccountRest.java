package com.example.accountservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
public class AccountRest {
    @Autowired
    private AccountDAO dao;

    @Autowired
    private AccountRepository repo;

    @RequestMapping("/create")
    public void create(@RequestParam("client_id") Integer clientId) {
        log.info("create account request");
        dao.create(clientId);
    }

    @RequestMapping("/fund/{id}")
    public boolean fund(@PathVariable Integer id, @RequestParam BigDecimal sum) {
        log.info("fund request for account");
        return dao.addBalance(id, sum.abs());
    }

    @RequestMapping("/checkout/{id}")
    public boolean checkout(@PathVariable Integer id, @RequestParam BigDecimal sum) {
        log.info("checkout request for account");
        return dao.addBalance(id, sum.abs().negate());
    }

    @RequestMapping("/get/{clientId}")
    public List<? extends Account> getByClient(@PathVariable Integer clientId) {
        log.info("getByClient request");
        return repo.findByClientId(clientId);
    }
}
