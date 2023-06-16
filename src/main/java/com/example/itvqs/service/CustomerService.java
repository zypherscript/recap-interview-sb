package com.example.itvqs.service;

import com.example.itvqs.entity.Customer;
import com.example.itvqs.repository.CustomerRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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

  @Transactional(rollbackFor = NoSuchElementException.class)
  public void updateCustomer(long id,
      String email) { //i kno this example is suck, but it's for demo ya kno
    var customer = customerRepository.findById(id).orElseThrow();
    customer.setEmail(email);
    customerRepository.save(customer);
    log.info("email {}", customer.getEmail());
    customerRepository.findById(-1L).orElseThrow();
  }

  @Cacheable(value = "customerCache")
  public List<Customer> findCustomers() {
    return customerRepository.findAll();
  }
}
