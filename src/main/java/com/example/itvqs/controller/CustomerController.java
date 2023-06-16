package com.example.itvqs.controller;

import com.example.itvqs.entity.Customer;
import com.example.itvqs.repository.CustomerRepository;
import com.example.itvqs.service.CustomerService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
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

  private final CustomerRepository customerRepository; //just for demo tho
  //https://siamchamnankit.co.th/%E0%B8%A5%E0%B8%AD%E0%B8%87%E0%B8%AA%E0%B8%A3%E0%B8%B8%E0%B8%9B-%E0%B8%81%E0%B8%8F%E0%B8%82%E0%B8%AD%E0%B8%87%E0%B8%81%E0%B8%B2%E0%B8%A3%E0%B8%AD%E0%B8%AD%E0%B8%81%E0%B9%81%E0%B8%9A%E0%B8%9A-uris-%E0%B8%88%E0%B8%B2%E0%B8%81%E0%B8%AB%E0%B8%99%E0%B8%B1%E0%B8%87%E0%B8%AA%E0%B8%B7%E0%B8%AD-rest-api-design-rulebook-1fb2e3a7258f
  private final CustomerService customerService;

  @GetMapping()
  ResponseEntity<List<Customer>> customers(Principal user) {
    log.info(user.getName());
    return ResponseEntity.ok(customerService.findCustomers());
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
