package com.example.itvqs.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestComponent {

  private final int index;

  public TestComponent(int index) {
    this.index = index;
  }

  public void test() {
    log.info("Calling Test Method %s".formatted(this.index));
  }
}
