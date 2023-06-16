package com.example.itvqs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.itvqs.config.TestComponent;
import com.example.itvqs.entity.Customer;
import com.example.itvqs.repository.CustomerRepository;
import com.example.itvqs.service.CustomerService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
@ActiveProfiles("test")
class ItvQsApplicationTests {

  @Autowired
  @Qualifier("testComponent1")
  private TestComponent testComponent;

  @Test
  void contextLoads(CapturedOutput output) {
    testComponent.test();
    Assertions.assertTrue(output.getOut().contains("Calling Test Method 1"));
  }

  @MockBean
  private CustomerRepository customerRepository;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private CacheManager cacheManager;

  @Test
  void givenRedisCaching_whenFindItemById_thenItemReturnedFromCache() {
    cacheManager.getCacheNames() //can be in before, clear bcuz using compose
        .parallelStream()
        .map(c -> cacheManager.getCache(c))
        .filter(Objects::nonNull)
        .forEach(Cache::clear);

    var customer = new Customer(1L, "test", "test@example.com");
    var customers = List.of(customer);
    when(customerRepository.findAll()).thenReturn(customers);

    var itemCacheMiss = customerService.findCustomers();
    var itemCacheHit = customerService.findCustomers();

    assertThat(itemCacheMiss).isEqualTo(customers);
    assertThat(itemCacheHit).isEqualTo(customers);

    verify(customerRepository, times(1)).findAll();

    var cacheObj = cacheManager.getCache("customerCache").get("findCustomers").get();
    assertThat(cacheObj).isEqualTo(customers);
  }

}
