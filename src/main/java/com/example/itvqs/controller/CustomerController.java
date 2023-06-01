package com.example.itvqs.controller;

import com.example.itvqs.entity.Customer;
import com.example.itvqs.repository.CustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerRepository customerRepository;

  @GetMapping("/customers")
  List<Customer> customers() {
    return customerRepository.findAll();
  }

  @GetMapping("/customers/{name}")
  public List<Customer> findByName(@PathVariable String name) {
    return customerRepository.findByName(name);
  }

  @GetMapping("/customers/query/{name}")
  public List<Customer> findByNameWithQuery(@PathVariable String name) {
    return customerRepository.findByNameWithQuery(name);
  }

  @GetMapping("/customers/native/{name}")
  public List<Customer> findByNameWithNativeQuery(@PathVariable String name) {
    return customerRepository.findByNameWithNativeQuery(name);
  }

  @GetMapping("/customers/custom/{name}")
  public List<Customer> findByNameWithNativeCustomQuery(@PathVariable String name) {
    return customerRepository.findByNameWithNativeCustomQuery(name);
  }

  @GetMapping("/customers/predicate/{name}")
  public List<Customer> queryCustomersByName(@PathVariable String name) {
    return customerRepository.queryCustomersByName(name);
  }
}
