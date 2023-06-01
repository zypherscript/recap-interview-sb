package com.example.itvqs.repository;

import com.example.itvqs.entity.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long>,
    CustomCustomerRepository {

  List<Customer> findByName(String name);

  //OR
  @Query("SELECT c FROM Customer c WHERE c.name = :value")
  List<Customer> findByNameWithQuery(String value);

  @Query(value = "SELECT * FROM customers WHERE customers.name = :value", nativeQuery = true)
  List<Customer> findByNameWithNativeQuery(String value);
}
