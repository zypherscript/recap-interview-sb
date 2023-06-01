package com.example.itvqs;

import com.example.itvqs.entity.Customer;
import com.example.itvqs.repository.CustomerRepository;
import java.util.stream.IntStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ItvQsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ItvQsApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(CustomerRepository customerRepository) {
    return args -> {
      var posts = IntStream.range(0, 2)
          .boxed()
          .map(i -> new Customer(null, "name".concat(String.valueOf(i)),
              "email%s@example.com".formatted(String.valueOf(i))))
          .toList();
      customerRepository.saveAll(posts);
    };
  }

}
