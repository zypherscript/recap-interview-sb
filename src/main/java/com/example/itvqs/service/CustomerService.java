package com.example.itvqs.service;

import com.example.itvqs.dto.CustomerDTO;
import com.example.itvqs.entity.Customer;
import com.example.itvqs.mapper.CustomerMapper;
import com.example.itvqs.repository.CustomerRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  public List<Customer> saveAll(List<Customer> customers) {
    return customerRepository.saveAll(customers);
  }

  @Retryable(retryFor = {RuntimeException.class},
      maxAttempts = 2,
      backoff = @Backoff(delay = 1000))
  @Transactional(rollbackFor = RuntimeException.class)
  public void updateCustomer(long id,
      String email) { //i kno this example is suck, but it's for demo ya kno
    var customer = customerRepository.findById(id).orElseThrow();
    customer.setEmail(email);
    customerRepository.save(customer);
    log.info("email: {}", customer.getEmail());
    throw new RuntimeException("Exception occurred");
  }

  @Cacheable(value = "customerCache", key = "#root.methodName")
  public List<Customer> findCustomers() {
    return customerRepository.findAll();
  }

  @Async
  public CompletableFuture<List<CustomerDTO>> findAllAsync() {
    var users = customerRepository.findAllAsync();
    return users.thenApply(
        customers -> customers.stream().map(CustomerMapper.INSTANCE::customerToCustomerDTO)
            .toList());
  }
}
