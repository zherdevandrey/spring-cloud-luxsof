package com.example.accountservice;

import java.math.BigDecimal;

public interface Account {
    Integer getId();
    Integer getClientId();
    BigDecimal getBalance();
}
