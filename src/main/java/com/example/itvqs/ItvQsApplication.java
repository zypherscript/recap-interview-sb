package com.example.itvqs;

import com.example.itvqs.config.TestComponent;
import com.example.itvqs.config.TestConfig;
import com.example.itvqs.entity.Customer;
import com.example.itvqs.repository.CustomerRepository;
import java.util.stream.IntStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ItvQsApplication {

  public static void main(String[] args) {
    var context = new AnnotationConfigApplicationContext(TestConfig.class);
    var testComponent1 = context.getBean("testComponent1", TestComponent.class);
    testComponent1.test();
    var testComponent2 = context.getBean("testComponent2", TestComponent.class);
    testComponent2.test();

    SpringApplication.run(ItvQsApplication.class, args);
  }

  @Bean
  @Profile("!test")
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
