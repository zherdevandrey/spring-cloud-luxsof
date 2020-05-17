package com.example.conversionservice.resource;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange", url = "${EXCHANGE_SERVICE_SERVICE_HOST:http://localhost}:8000")//
//@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000")
@FeignClient(name = "exchange-service", url = "${EXCHANGE_SERVICE_URI:http://localhost}:8000")//Kubernetes Service Name
public interface CurrencyExchangeServiceProxy {
	//http://localhost:8000/currency-exchange/from/USD/to/INR
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from,
			@PathVariable("to") String to);
}