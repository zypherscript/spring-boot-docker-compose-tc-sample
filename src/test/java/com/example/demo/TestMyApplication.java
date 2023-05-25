package com.example.demo;

import org.springframework.boot.SpringApplication;

public class TestMyApplication {

  public static void main(String[] args) {
    SpringApplication.from(DemoApplication::main).with(TestContainersConfiguration.class).run(args);
  }
}
