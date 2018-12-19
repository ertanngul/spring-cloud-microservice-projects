package com.in28minutes.microservices.limitsservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;

@RestController
public class LimitsConfigurationController {

  @Autowired
  Configuration configuration;

  @GetMapping("/limits")
  public LimitConfiguration retrieveLimitsFromConfigurations() {
    return new LimitConfiguration(configuration.getMinimum(), configuration.getMaximum());
  }

  @GetMapping("/fault-tolerance-exception")
  @HystrixCommand(fallbackMethod = "fallbackRetrieveConfigurations")
  public LimitConfiguration retrieveConfigurations() {
    throw new RuntimeException("Availability exception");
  }

  public LimitConfiguration fallbackRetrieveConfigurations() {
    return new LimitConfiguration(1234, 9876);
  }

}
