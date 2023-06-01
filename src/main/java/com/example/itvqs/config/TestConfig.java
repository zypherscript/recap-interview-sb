package com.example.itvqs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

  @Bean(name = "testComponent1")
  public TestComponent testComponentBean1() {
    return new TestComponent(1);
  }

  @Bean(name = "testComponent2")
  public TestComponent testComponentBean2() {
    return new TestComponent(2);
  }
}
