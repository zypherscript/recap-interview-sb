package com.example.itvqs.controller;

import com.example.itvqs.entity.Customer;
import com.example.itvqs.repository.CustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

  /*
      produces: The default value is MediaType.APPLICATION_JSON_VALUE. This means
      that if the produces attribute is not explicitly specified in the @RestController
      annotation or in a specific request mapping method, the controller will produce JSON
      responses by default.

      consumes: The default value is an empty array, which means there are no specific
      restrictions on the content types that the controller can consume. In other words, it can
      consume any content type specified in the request.
   */

  private final CustomerRepository customerRepository;

  @GetMapping()
  ResponseEntity<List<Customer>> customers() {
    return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{name}")
  public List<Customer> findByName(@PathVariable String name) {
    return customerRepository.findByName(name);
  }

  @GetMapping("/query/{name}")
  public List<Customer> findByNameWithQuery(@PathVariable String name) {
    return customerRepository.findByNameWithQuery(name);
  }

  @GetMapping("/native/{name}")
  public List<Customer> findByNameWithNativeQuery(@PathVariable String name) {
    return customerRepository.findByNameWithNativeQuery(name);
  }

  @GetMapping("/custom/{name}")
  public List<Customer> findByNameWithNativeCustomQuery(@PathVariable String name) {
    return customerRepository.findByNameWithNativeCustomQuery(name);
  }

  @GetMapping("/predicate/{name}")
  public List<Customer> queryCustomersByName(@PathVariable String name) {
    return customerRepository.queryCustomersByName(name);
  }
}
